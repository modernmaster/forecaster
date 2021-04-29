import { BrowserRouter as Route, Link } from "react-router-dom";

class Stock extends React.Component {

    constructor(props) {
        super(props);
        this.state = {changeEvent: ''}
    }

      componentDidUpdate(prevProps, prevState) {
         if(typeof this.props.changeEvent !== 'undefined') {
            if( prevProps.changeEvent!==this.state.changeEvent) {
                setTimeout(this.setState({changeEvent: this.props.changeEvent}),1000);
                this.setTimer();
            }
         }
      }

    setTimer(){
        this._timer != null ? clearTimeout(this._timer) : null;
        this._timer = setTimeout(function(){
            this.setState({changeEvent: ''})
            this._timer = null;
        }.bind(this), 5000);
    }

  render() {
    let className = 'stock';
    if (this.state.changeEvent !== '') {
        className += ' ' + this.props.changeEvent;
    }
    return (
        <tr className={className}>
          <td>{this.props.stock.companyName}</td>
          <td><Link to={`/ui/stock/${this.props.stock.symbol}`}>{this.props.stock.symbol}</Link></td>
          <td>{this.props.stock.percentageChange}</td>
          <td>{this.props.stock.volume}</td>
          <td>{this.props.stock.avgVolume}</td>
        </tr>
    )
  }
}

export default Stock;
