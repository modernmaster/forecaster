'use strict';

import React from 'react';
import StockList from "../../component/stock-list/index.js";

const ReactDOM = require('react-dom');
const client = require('../../api/client');
const follow = require('../../api/follow');
const stompClient = require('../../websocket-listener');
const when = require('when');
const root = "https://localhost/stock-service/api/stocks/search";

//allow react dev tools work
window.React = React;

class Patterns extends React.Component {
    constructor(props) {
        super(props);
        this.state = {stocks: [], attributes: [], pageSize: 30, pageIndex: 0, links: {}};
        this.onNavigate = this.onNavigate.bind(this);
    }

    componentDidMount() {
        this.loadFromServer(this.state.pageIndex, this.state.pageSize);
    }

    loadFromServer(pageIndex, pageSize) {
        follow(client, root, [
                {rel: "findByPatterns_PatternType", params: {patternType:'BULLISH', size: pageSize, page: pageIndex, sort:'percentageChange,desc'}}]
        ).then(stockCollection => {
        	this.links = stockCollection.entity._links;
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
                links: this.links,
                stocks,
                pageSize: pageSize
            });
        });
    }

    onNavigate(navUri) {
        var pageSize = navUri.substring(navUri.indexOf("size=")+5);
        pageSize = pageSize.substring(0, pageSize.indexOf("&"));
        var pageIndex = navUri.substring(navUri.indexOf("page=")+5);
        pageIndex = pageIndex.substring(0, pageIndex.indexOf("&"));
        this.loadFromServer(pageIndex, pageSize);
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