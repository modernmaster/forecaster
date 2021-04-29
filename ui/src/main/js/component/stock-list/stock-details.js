import Chart from '../chart/index.js';
class StockDetails extends React.Component {

  constructor(props) {
    super(props);
  }

  render() {
    var symbol = this.props.match.params.topicId;
    var stock = this.props.stocks.find(x=>x.key===symbol).props.stock;
    var patterns = [];
    if(stock.patterns) {
	    patterns = stock.patterns.map(pattern =>
			<tr>
			    <td>{pattern.patternType}</td>
			    <td>{pattern.window}</td>
			    <td>{pattern.identifiedPrice}</td>
			    <td>{pattern.targetPrice}</td>
			    <td>{pattern.dateTimeCreated}</td>
			    <td>{pattern.identifiedPrice - stock.price}</td>
			</tr>
		);
    }

    return (
        <aside className="col-6">
          <h2>{symbol}</h2>
          <figure>
            <Chart stock={stock}/>
          </figure>
          <ul className="nav nav-tabs">
            <li className="nav-item">
              <a className="nav-link active" href="#details" data-toggle="tab"><h5>Stock Details</h5></a>
            </li>
            <li className="nav-item">
               <a className="nav-link" href="#trends" data-toggle="tab"><h5>Current Trends</h5></a>
            </li>
            <li className="nav-item">
               <a className="nav-link" href="#patterns" data-toggle="tab"><h5>Current Patterns</h5></a>
            </li>
           </ul>
          <div className="tab-content">
          <section role="tabpanel" className="tab-pane container active" id="details">
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
          <table>
            <thead>
             <tr>
                <td>Trend</td>
                <td>Value</td>
                <td>Peak</td>
                <td>Trough</td>
                <td>Price target for next peak</td>
             </tr>
            </thead>
           <tbody>
             <tr>
                <td>MOVING_AVERAGE_DURATION_9</td>
                <td>0</td>
                <td>0</td>
                <td>0</td>
                <td>0</td>
             </tr>
             <tr>
                <td>MOVING_AVERAGE_DURATION_20</td>
                <td>0</td>
                <td>0</td>
                <td>0</td>
                <td>0</td>
             </tr>
             <tr>
                <td>RSI_14</td>
                <td>0</td>
                <td>0</td>
                <td>0</td>
                <td>0</td>
             </tr>
             <tr>
                <td>MOVING_AVERAGE_DURATION_200</td>
                <td>0</td>
                <td>0</td>
                <td>0</td>
                <td>0</td>
             </tr>
             <tr>
                <td>MOVING_AVERAGE_DURATION_50</td>
                <td>0</td>
                <td>0</td>
                <td>0</td>
                <td>0</td>
             </tr>
           </tbody>
         </table>
      </section>
      <section role="tabpanel" className="tab-pane container" id="patterns">
          <table>
            <thead>
             <tr>
			    <td>Pattern</td>
			    <td>Window</td>
			    <td>Identified Price</td>
			    <td>Target Price</td>
			    <td>DateTime Created</td>
			    <td>Change in price</td>
             </tr>
            </thead>
              <tbody>
                  {patterns}
              </tbody>
          </table>
      </section>
      </div>
        </aside>
    )
  }
}
export default StockDetails;