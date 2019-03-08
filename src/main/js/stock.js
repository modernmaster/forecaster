import { BrowserRouter as Route, Link } from "react-router-dom";

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
          <td>{this.props.stock.companyName}</td>
          <td><Link to={`/stock/${this.props.stock.symbol}`}>{this.props.stock.symbol}</Link></td>
          <td>{this.props.stock.percentageChange}</td>
          <td>{this.props.stock.volume}</td>
          <td>{this.props.stock.avgVolume}</td>
        </tr>
    )
  }
}

export default Stock;
