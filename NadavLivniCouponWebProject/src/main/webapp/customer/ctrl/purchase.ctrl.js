(function() {

	var module = angular.module("couponSystem");

	module.controller("PurchaseCouponCtrl", PurchaseCouponCtrlCtor);

	// Ctor method for the PurchaseCouponCtrl
	function PurchaseCouponCtrlCtor(mainCustomerServiceHTTP, $scope) {
		this.mycoupons = [];
		this.success = false;
		this.failure = false;

		var promiseGet = mainCustomerServiceHTTP.getAllPurchasedCoupons();
		promiseGet.then(function(resp) {
			self.mycoupons = resp.data;
		}, function(err) {
			$('#errorDIv').show();
			$('#errorDIv').text(data.responseText);
		});

		this.purchaseCoupon = function(id) {

			this.success = false;
			this.failure = false;
			var self = this;

			var promisePost = mainCustomerServiceHTTP.purchaseCoupon(this.id);
			promisePost.then(function(resp) {
				// self.coupons = resp.data;
				location.reload();
				self.success = true;
				self.failure = false;
			}, function(err) {
				self.success = false;
				self.failure = true;
			});

		}

	}

})();