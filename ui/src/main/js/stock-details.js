import Chart from './chart.js';
class StockDetails extends React.Component {

  constructor(props) {
    super(props);
  }

  render() {
    var symbol = this.props.match.params.topicId;
    var stock = this.props.stocks.find(x=>x.key===symbol).props.stock;

    return (
        <aside className="col-6">
          <h2>{symbol}</h2>
          <figure>
            <Chart stock={stock}/>
          </figure>
          <ul className="nav nav-tabs">
            <li className="nav-item">
              <a className="nav-link active" href="#details" data-toggle="tab">Stock Details</a>
            </li>
            <li className="nav-item">
               <a className="nav-link" href="#trends" data-toggle="tab">Current Trends</a>
            </li>
           </ul>
          <div className="tab-content">
          <section role="tabpanel" className="tab-pane container active" id="details">
          <h3>Stock details</h3>
          <table>
          <tbody>
              <tr>
                                      <td>Admission Date</td>

                <td>{stock.admissionDate}</td>
              </tr>
            <tr>
                                    <td>Company Name</td>

                <td>{stock.companyName}</td>
            </tr>
              <tr>
                                      <td>Symbol</td>

                              <td>{stock.symbol}</td>

              </tr>
              <tr>
                                      <td>ICB Industry</td>

                <td>{stock.icbIndustry}</td>
              </tr>
              <tr>
                                      <td>ICB Super Sector</td>

                <td>{stock.icbSuperSector}</td>
              </tr>
              <tr>
                                                    <td>Country Of Incorporation</td>

                <td>{stock.countryOfIncorporation}</td>
              </tr>
              <tr>
                                                                  <td>World Region</td>

                <td>{stock.worldRegion}</td>
              </tr>
              <tr>
                                                                                <td>Market</td>

                <td>{stock.market}</td>
               </tr>
               <tr>
                                                                                               <td>International Issuer</td>

                <td>{stock.internationalIssuer}</td>
               </tr>
               <tr>
                                                                                                              <td>Company Market Cap</td>

                <td>{stock.companyMarketCap}</td>
               </tr>
               <tr>
                 <td>Price</td>

                <td>{stock.price}</td>
               </tr>
               <tr>
                <td>Percentage Change</td>

                <td>{stock.percentageChange}</td>
               </tr>
               <tr>
                               <td>Volume</td>

                <td>{stock.volume}</td>
               </tr>
               <tr>
                                          <td>Avg Volume</td>

                <td>{stock.avgVolume}</td>
               </tr>
               <tr>
                                                         <td>PE</td>

                <td>{stock.pe}</td>
               </tr>
               <tr>
                                                                        <td>High 52</td>

                <td>{stock.high52}</td>
               </tr>
               <tr>
                                                                                       <td>Low 52</td>

                <td>{stock.low52}</td>
               </tr>
               <tr>
                                                                                                      <td>Delay</td>

                <td>{stock.delay}</td>
               </tr>
          </tbody>

          </table>
        </section>
      <section role="tabpanel" className="tab-pane container" id="trends">
          <h3>Current Trends</h3>
          List of trends here...
      </section>
      </div>
        </aside>
    )
  }
}
export default StockDetails;