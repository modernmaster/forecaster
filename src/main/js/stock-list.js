import { BrowserRouter as Router, Route, Link } from "react-router-dom";
const Stock = require('./stock.js');
const StockDetails = require('./stock-details.js');


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
var divStyleLeft = {
                                              float: 'left',
                                              width: '50%'};
    return (
    <main>
    <article style={divStyleLeft}>
          <table>
              <thead>
            <tr>
              <th>Company Name</th>
              <th>Symbol</th>
              <th>Percentage Change</th>
              <th>Volume</th>
              <th>Avg Volume</th>
            </tr>
            </thead>
            <tbody>
            {stocks}
            </tbody>
          </table>
          <div>
            {navLinks}
          </div>
          </article>
          <Route path={`/stock/:topicId`}
           render = {(props) => <StockDetails {...props} stocks={stocks} />} />
          <Route exact path='stock' render={() => <h3>Please select a topic.</h3>}
          />
          </main>
    )
  }
}

module.exports = StockList;