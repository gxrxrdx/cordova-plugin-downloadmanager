var exec = require('cordova/exec');

exports.download = function(arg0, arg1, success, error) {
	console.log('arg0: ',arg0,'arg1: ',arg1);
    exec(success, error, "DownloadManager", "download", [arg0,arg1]);
};
