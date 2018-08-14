'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
const follow = require('./follow');
const root = '/api';
const PushNotification = require('./push-notification');

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
        <main>
        <PushNotification/>
        <StockList stocks={this.state.stocks}
                   links={this.state.links}
                   pageSize={this.state.pageSize}
                   onNavigate={this.onNavigate}
                   updatePageSize={this.updatePageSize}/>
        </main>
    )
  }
}

class StockList extends React.Component {
  constructor(props) {
    super(props);
    this.handleNavFirst = this.handleNavFirst.bind(this);
    this.handleNavPrev = this.handleNavPrev.bind(this);
    this.handleNavNext = this.handleNavNext.bind(this);
    this.handleNavLast = this.handleNavLast.bind(this);
    this.handleInput = this.handleInput.bind(this);
  }

  handleInput(e) {
    e.preventDefault();
    var pageSize = ReactDOM.findDOMNode(this.refs.pageSize).value;
    if (/^[0-9]+$/.test(pageSize)) {
      this.props.updatePageSize(pageSize);
    } else {
      ReactDOM.findDOMNode(this.refs.pageSize).value =
          pageSize.substring(0, pageSize.length - 1);
    }
  }

  handleNavFirst(e) {
    e.preventDefault();
    this.props.onNavigate(this.props.links.first.href);
  }

  handleNavPrev(e) {
    e.preventDefault();
    this.props.onNavigate(this.props.links.prev.href);
  }

  handleNavNext(e) {
    e.preventDefault();
    this.props.onNavigate(this.props.links.next.href);
  }

  handleNavLast(e) {
    e.preventDefault();
    this.props.onNavigate(this.props.links.last.href);
  }

  render() {
    var stocks = this.props.stocks.sort(
        (a, b) => b.percentageChange - a.percentageChange).map(stock =>
        <Stock key={stock.symbol} stock={stock}/>
    );
    var navLinks = [];
    // 	if ("first" in this.props.links) {
    // 		navLinks.push(<button key="first" onClick={this.handleNavFirst}>&lt;&lt;</button>);
    // 	}
    // 	if ("prev" in this.props.links) {
    // 		navLinks.push(<button key="prev" onClick={this.handleNavPrev}>&lt;</button>);
    // 	}
    // 	if ("next" in this.props.links) {
    // 		navLinks.push(<button key="next" onClick={this.handleNavNext}>&gt;</button>);
    // 	}
    // 	if ("last" in this.props.links) {
    // 		navLinks.push(<button key="last" onClick={this.handleNavLast}>&gt;&gt;</button>);
    // 	}

    return (
        <div>
          <input ref="pageSize" defaultValue={this.props.pageSize}
                 onInput={this.handleInput}/>
          <table>
            <tbody>
            <tr>
              <th>Admission Date</th>
              <th>Company Name</th>
              <th>Symbol</th>
              <th>ICB Industry</th>
              <th>ICB Super Sector</th>
              <th>Country Of Incorporation</th>
              <th>World Region</th>
              <th>Market</th>
              <th>International Issuer</th>
              <th>Company Market Cap</th>
              <th>Price</th>
              <th>Percentage Change</th>
              <th>Volume</th>
              <th>Avg Volume</th>
              <th>PE</th>
              <th>High 52</th>
              <th>Low 52</th>
              <th>Delay</th>
            </tr>
            {stocks}
            </tbody>
          </table>
          <div>
            {navLinks}
          </div>
        </div>
    )
  }
}

class Stock extends React.Component {

  constructor(props) {
    super(props);
  }

  render() {
    let className = 'stock';
    if (this.props.stock._changeEvent) {
      className += ' ' + this.props.stock._changeEvent;
    }

    return (
        <tr className={className}>
          <td>{this.props.stock.admissionDate}</td>
          <td>{this.props.stock.companyName}</td>
          <td>{this.props.stock.symbol}</td>
          <td>{this.props.stock.icbIndustry}</td>
          <td>{this.props.stock.icbSuperSector}</td>
          <td>{this.props.stock.countryOfIncorporation}</td>
          <td>{this.props.stock.worldRegion}</td>
          <td>{this.props.stock.market}</td>
          <td>{this.props.stock.internationalIssuer}</td>
          <td>{this.props.stock.companyMarketCap}</td>
          <td>{this.props.stock.price}</td>
          <td>{this.props.stock.percentageChange}</td>
          <td>{this.props.stock.volume}</td>
          <td>{this.props.stock.avgVolume}</td>
          <td>{this.props.stock.pe}</td>
          <td>{this.props.stock.high52}</td>
          <td>{this.props.stock.low52}</td>
          <td>{this.props.stock.delay}</td>
        </tr>
    )
  }
}

ReactDOM.render(
    <App/>,
    document.getElementById('react')
)