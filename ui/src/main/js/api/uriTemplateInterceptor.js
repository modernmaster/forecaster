define(function(require) {
	'use strict';

	const interceptor = require('rest/interceptor');

	return interceptor({
		request: function (request /*, config, meta */) {
                /* If the URI is a URI Template per RFC 6570 (https://tools.ietf.org/html/rfc6570), trim out the template part */
                if (request.path.indexOf('{') === -1) {
                    return request;
                } else {
                var uri = request.path.split('{')[0];
                var result = [uri];
                result.push('?');
                var map = new Map(Object.entries(request.params));
                map.forEach(function(value, key, map) {
                    if(request.path.indexOf(key)>-1) {
                        result.push(key + '=' + value);
                        result.push('&');
                    }
                });
                result.pop();
                request.path = result.join('');
				return request;
			}
		}
	});

});