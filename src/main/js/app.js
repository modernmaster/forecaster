'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
// end::vars[]


class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {stocks: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/stocks'}).done(response => {
			this.setState({stocks: response.entity._embedded.stocks});
		});
	}

	render() {
		return (
			<StockList stocks={this.state.stocks}/>
		)
	}
}


class StockList extends React.Component{
	render() {
		var stocks = this.props.stocks.map(stock =>
			<Stock key={stock._links.self.href} stock={stock}/>
		);
		return (
			<table>
				<tbody>
					<tr>
				<th>Admission Date}</th>
				<th>Company Name}</th>
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
		)
	}
}

class Stock extends React.Component{
	render() {
		return (
			<tr>
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
	<App />,
	document.getElementById('react')
)