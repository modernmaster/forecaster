'use strict';

import { BrowserRouter as Router, Route, Link } from "react-router-dom";
const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
const follow = require('./follow');
const root = '/api';
const PushNotification = require('./push-notification');
import registerServiceWorker from './register-service-worker';
const StockList = require('./stock-list.js');

const stompClient = require('./websocket-listener');

class App extends React.Component {

  constructor(props) {
    super(props);
    this.state = {stocks: [], attributes: [], pageSize: 100, links: {}};
    this.updatePageSize = this.updatePageSize.bind(this);
    this.onNavigate = this.onNavigate.bind(this);
    this.refreshCurrentPage = this.refreshCurrentPage.bind(this);
  }

  componentDidMount() {
    this.loadFromServer(this.state.pageSize);
    stompClient.register([
      {route: '/topic/newstock', callback: this.refreshAndGoToLastPage},
      {route: '/topic/updatestock', callback: this.refreshCurrentPage},
      {route: '/topic/deletestock', callback: this.refreshCurrentPage}
    ]);
  }

  loadFromServer(pageSize) {
    follow(client, root, [
      {rel: 'stocks', params: {size: pageSize}}]
    ).then(stockCollection => {
      return client({
        method: 'GET',
        path: stockCollection.entity._links.profile.href,
        headers: {'Accept': 'application/schema+json'}
      }).then(schema => {
        this.schema = schema.entity;
        return stockCollection;
      });
    }).done(stockCollection => {
      this.setState({
        stocks: stockCollection.entity._embedded.stocks,
        attributes: Object.keys(this.schema.properties),
        pageSize: pageSize,
        links: stockCollection.entity._links
      });
    });
  }

  onNavigate(navUri) {
    client({method: 'GET', path: navUri}).done(stockCollection => {
      this.setState({
        stocks: stockCollection.entity._embedded.stocks,
        attributes: this.state.attributes,
        pageSize: this.state.pageSize,
        links: stockCollection.entity._links
      });
    });
  }

  updatePageSize(pageSize) {
    if (pageSize !== this.state.pageSize) {
      this.loadFromServer(pageSize);
    }
  }

  refreshAndGoToLastPage(message) {
    follow(client, root, [{
      rel: 'stocks',
      params: {size: this.state.pageSize}
    }]).done(response => {
      if (response.entity._links.last !== undefined) {
        this.onNavigate(response.entity._links.last.href);
      } else {
        this.onNavigate(response.entity._links.self.href);
      }
    })
  }

  refreshCurrentPage(message) {
    //Add new property to deal with _changeEvent
    let updatedStock = JSON.parse(message.body);
    let stock = this.state.stocks.find(
        x => x.companyName === updatedStock.companyName);
    if (parseInt(updatedStock.price) > parseInt(stock.price)) {
      updatedStock._changeEvent = 'increase';

    } else if (parseInt(updatedStock.price) < parseInt(stock.price)) {
      updatedStock._changeEvent = 'decrease';
    }

    this.setState({
      page: this.page,
      stocks: this.state.stocks.map(
          x => x.companyName === updatedStock.companyName
              ? updatedStock : x),
      attributes: Object.keys(this.schema.properties),
      pageSize: this.state.pageSize,
      links: this.links
    });
  }

  // console.log("REFRESH PAGE");
  // 	follow(client, root, [{
  // 		rel: 'stocks',
  // 		params: {
  // 			size: this.state.pageSize
  // 		}
  // 	}]).then(stockCollection => {
  // 		this.links = stockCollection.entity._links;
  // 		this.page = stockCollection.entity.page;
  //
  // 		return stockCollection.entity._embedded.stocks.map(stock => {
  // 			return client({
  // 				method: 'GET',
  // 				path: stock._links.self.href
  // 			})
  // 		});
  // 	// })
  //     // .then(stockPromises => {
  // 	// 	return when.all(stockPromises);
  // 	}).then(stocks => {
  // 		this.setState({
  // 			page: this.page,
  // 			stocks: stocks,
  // 			attributes: Object.keys(this.schema.properties),
  // 			pageSize: this.state.pageSize,
  // 			links: this.links
  // 		});
  // 	});

  render() {

    return (
      <Router>
        <main>
          <PushNotification/>
          <StockList stocks={this.state.stocks}
                     links={this.state.links}
                     pageSize={this.state.pageSize}
                     onNavigate={this.onNavigate}
                     updatePageSize={this.updatePageSize}/>
        </main>
      </Router>
    )
  }
}

ReactDOM.render(
    <App/>,
    document.getElementById('react')
);

registerServiceWorker.register();