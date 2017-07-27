(function() {

	var module = angular.module("couponSystem");

	module.controller("CreateCustomerCtrl", CreateCustomerCtrlCtor);

	function CreateCustomerCtrlCtor(mainAdminServiceHTTP) {
		var self = this;
		this.newCustomer;
		this.success = false;
		this.failure = false;

		this.createCustomer = function() {
			this.createButton = true;
			this.pressedButton = function() {
				this.createButton = true;
			}
			if (this.newCustomer == undefined
					|| this.newCustomer.id == undefined
					|| this.newCustomer.custName == undefined
					|| this.newCustomer.password == undefined) {
				this.success = false;
				this.failure = true;
				return;
			}

			this.success = false;
			this.failure = false;
			var self = this;

			var promisePost = mainAdminServiceHTTP
					.createCustomer(this.newCustomer);
			promisePost.then(function(resp) {
				self.customers = resp.data;
				self.newCustomer = {};
				self.success = true;
				self.failure = false;

			}, function(err) {
				if (err.status == 404) {
					window.location.replace("../404.html");
				}
				self.success = false;
				self.failure = true;
			});

		}

	}

})();