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

class Patterns extends React.Component {
    constructor(props) {
        super(props);
        this.state = {stocks: [], attributes: [], pageSize: 30, links: {}};
    }

    componentDidMount() {
        this.loadFromServer(this.state.pageSize);
    }

    loadFromServer(pageSize) {
        follow(client, root, [
                {rel: "stocks/search/findByPatterns_PatternType", params: {patternType='BULLISH', size: pageSize, sort:'percentageChange,desc'}}]
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
                stocks,
                attributes: Object.keys(this.schema.properties),
                pageSize: pageSize,
                links: this.links
            });
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
};

export default Patterns;