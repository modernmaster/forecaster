import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import Stock from './stock.js';
import StockDetails from './stock-details.js';
const ReactDOM = require('react-dom');

class StockList extends React.Component {
  constructor(props) {
    super(props);
    this.handleNavFirst = this.handleNavFirst.bind(this);
    this.handleNavPrev = this.handleNavPrev.bind(this);
    this.handleNavNext = this.handleNavNext.bind(this);
    this.handleNavLast = this.handleNavLast.bind(this);
    this.handleInput = this.handleInput.bind(this);
    this.handleInputSearch = this.handleInputSearch.bind(this);
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

  handleInputSearch(e) {
       e.preventDefault();
       var searchCriteria = ReactDOM.findDOMNode(this.refs.search).value;
       if (searchCriteria.length > 3) {
         this.props.onSearch(searchCriteria);
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

		const stocks = this.props.stocks.map(stock =>
			<Stock key={stock.entity.symbol}
			//{stocks.entity._links.self.href}
					  stock={stock.entity}
					  attributes={this.props.attributes}
					  changeEvent={stock._changeEvent}/>
//					  onUpdate={this.props.onUpdate}
//					  onDelete={this.props.onDelete}/>
		);

    var navLinks = [];
     	if ("first" in this.props.links) {
     		navLinks.push(<button key="first" onClick={this.handleNavFirst}>&lt;&lt;</button>);
     	}
     	if ("prev" in this.props.links) {
     		navLinks.push(<button key="prev" onClick={this.handleNavPrev}>&lt;</button>);
     	}
     	if ("next" in this.props.links) {
     		navLinks.push(<button key="next" onClick={this.handleNavNext}>&gt;</button>);
     	}
     	if ("last" in this.props.links) {
     		navLinks.push(<button key="last" onClick={this.handleNavLast}>&gt;&gt;</button>);
     	}
    return (
    <div className="row">
        <article className="col-6">
              <div>
                {navLinks}
              </div>
              <div>
              	    <input ref="pageSize" defaultValue={this.props.pageSize} onInput={this.handleInput}/>
              	    <input ref="search" defaultValue="" onInput={this.handleInputSearch}/>
              </div>
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
        <Route path={`/ui/stock/:topicId`}
            render = {(props) => <StockDetails {...props} stocks={stocks} />} />
    </div>
    )
  }
}

export default StockList;