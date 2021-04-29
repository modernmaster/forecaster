'use strict';

import React from 'react';
import StockList from "../../component/stock-list/index.js";

const ReactDOM = require('react-dom');
const client = require('../../api/client');
const follow = require('../../api/follow');
const stompClient = require('../../websocket-listener');
const when = require('when');
const root = "https://localhost/stock-service/api";

//allow react dev tools work
window.React = React;

class Stocks extends React.Component {
  constructor(props) {
    super(props);
    this.state = {stocks: [], attributes: [], pageSize: 30, links: {}};
    this.updatePageSize = this.updatePageSize.bind(this);
    this.onNavigate = this.onNavigate.bind(this);
    this.refreshCurrentPage = this.refreshCurrentPage.bind(this);
    this.onSearch = this.onSearch.bind(this);
    this.updateStock = this.updateStock.bind(this);
  }
  componentDidMount() {
    this.loadFromServer(this.state.pageSize);
    stompClient.register([
      {route: '/topic/updateStock', callback: this.updateStock}
    ]);
  }
	loadFromServer(pageSize) {
		follow(client, root, [
				{rel: "stocks", params: {size: pageSize, sort:'percentageChange,desc'}}]
		).then(stockCollection => {
				return client({
					method: 'GET',
					path: stockCollection.entity._links.profile.href,
					headers: {"Accept": 'application/schema+json'}
				}).then((schema) => {
					this.schema = schema.entity;
					this.links = stockCollection.entity._links;
					return stockCollection;
				});
		}).then(stockCollection => {
			this.page = stockCollection.entity.page;
			return stockCollection.entity._embedded.stocks.map(stock =>
					client({
						method: 'GET',
						path: stock._links.self.href
					})
			);
		}).then(stockPromises => {
			return when.all(stockPromises);
		}).then(stocks => {
			this.setState({
				page: this.page,
				stocks: stocks,
				attributes: Object.keys(this.schema.properties),
				pageSize: pageSize,
				links: this.links
			});
		});
	}

    onNavigate(navUri) {
        this.searchForStockCollection(navUri);
    }

  updatePageSize(pageSize) {
    if (pageSize !== this.state.pageSize) {
      this.loadFromServer(pageSize);
    }
  }

  onSearch(searchCriteria) {
    if(searchCriteria.toUpperCase().indexOf('LON:')>-1) {
        this.searchBySymbol(searchCriteria);
    } else {
        this.searchByCompanyName(searchCriteria);
    }
  }

  searchByCompanyName(companyName) {
      const navUri = root+'/stocks/search/findByCompanyNameStartsWith?companyName='+companyName.toUpperCase();
      this.searchForStockCollection(navUri);
  }

  searchForStockCollection(navUri) {
      client({
          method: 'GET',
          path: navUri
      }).then(stockCollection => {
          this.links = stockCollection.entity._links;
          this.page = stockCollection.entity.page;
          return stockCollection.entity._embedded.stocks.map(stock =>
                  client({
                      method: 'GET',
                      path: stock._links.self.href
                  })
          );
      }).then(stockPromises => {
          return when.all(stockPromises);
      }).then(stocks => {
          this.setState({
              page: this.page,
              stocks: stocks,
              attributes: Object.keys(this.schema.properties),
              pageSize: this.state.pageSize,
              links: this.links
          });
      });
  }

  searchBySymbol(symbol) {
      const navUri = root+'/stocks/search/findBySymbol?symbol='+symbol.toUpperCase();
      client({
          method: 'GET',
          path: navUri
      }).then((stock) => {
          this.setState({
              page: this.page,
              stocks: [stock],
              pageSize: this.state.pageSize,
              links: this.links
          });
      });
  }

  updateStock(message) {
    let tmpStocks = this.state.stocks;
    let updatedStock = JSON.parse(message.body);
//    tmpStocks.push(updatedStock);
//    tmpStocks.sort(function(a, b) {
//        return b.entity.percentageChange - a.entity.percentageChange;
//    });
//    tmpStocks.slice(0, this.state.pageSize);
    let stock = tmpStocks.find((x) => x.entity.symbol===updatedStock.symbol);
    if(typeof stock !== "undefined") {
        updatedStock._changeEvent = "increase";
        if (parseInt(updatedStock.price) > parseInt(stock.price)) {
            updatedStock._changeEvent = 'increase';

        } else if (parseInt(updatedStock.price) < parseInt(stock.price)) {
            updatedStock._changeEvent = 'decrease';
        }
        stock.entity.price = updatedStock.price;
        stock.entity.percentageChange = updatedStock.percentageChange;
        stock.entity.symbol = updatedStock.symbol;
        stock.entity.volume = updatedStock.volume;
        tmpStocks.map(x => x.entity.symbol===stock.symbol? stock : x)
        this.setState({
            page: this.page,
            attributes: Object.keys(this.schema.properties),
            stocks: tmpStocks,
            pageSize: this.state.pageSize,
            links: this.links
        });
    }
  }

  refreshCurrentPage(message) {
  	follow(client, root, [{
  		rel: 'stocks',
  		params: {size: this.state.pageSize, sort:'percentageChange,desc', page: this.state.page.number}
  	}]).then((stockCollection) => {
              this.links = stockCollection.entity._links;
              this.page = stockCollection.entity.page;
              return stockCollection.entity._embedded.stocks.map(stock =>
                      client({
                          method: 'GET',
                          path: stock._links.self.href
                      })
              );
          }).then(stockPromises => {
              return when.all(stockPromises);
          });
  }
  render() {
    return (
      <StockList stocks={this.state.stocks}
               links={this.state.links}
               pageSize={this.state.pageSize}
               onNavigate={this.onNavigate}
               updatePageSize={this.updatePageSize}
               onSearch={this.onSearch}/>
      );
  }
}

export default Stocks;
