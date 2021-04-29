'use strict';

import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import PushNotification from './component/push-notification';
import Stocks from './page/stocks/index.js';
import Patterns from './page/patterns/index.js';

const React = require('react');
const ReactDOM = require('react-dom');

class App extends React.Component {

  constructor(props) {
    super(props);
  }

  componentDidMount() {
  }

  render() {

    return (
        <Router>
        <PushNotification/>
        <Switch>
          <Route path="/ui/patterns/" component={Patterns}/>
          <Route path="/ui/" component={Stocks}/>
        </Switch>
        </Router>
    )
  }
}

ReactDOM.render(
    <App/>,
    document.getElementById('react')
);
