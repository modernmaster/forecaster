var path = require('path');

var node_dir = __dirname + '/node_modules';

module.exports = {
    entry: [
    './src/main/js/app.js',
    './src/main/js/stock.js',
    './src/main/js/stock-details.js',
    './src/main/js/stock-list.js',

    './src/main/js/push-notification.js'],
    devtool: 'sourcemaps',
    cache: true,
    mode: 'development',
    resolve: {
        alias: {
            'stompjs': node_dir + '/stompjs/lib/stomp.js',
        }
    },
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: /(node_modules)/,
                use: {
                    loader: "babel-loader",
                    options: {
                      presets: ['@babel/react','@babel/preset-env']
                    }
                }
            }
        ]
    }
};