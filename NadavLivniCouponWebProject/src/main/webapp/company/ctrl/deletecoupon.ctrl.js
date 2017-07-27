(function() {

	var module = angular.module("couponSystem");

	module.controller("DeleteCouponCtrl", DeleteCouponCtrlCtor);

	function DeleteCouponCtrlCtor(mainCompanyService) {
		this.coupons = [];
		var self = this;

		var promiseGet = mainCompanyService.getAllCoupons();
		promiseGet.then(function(resp) {

			self.coupons = resp.data;

		}, function(err) {
			alert(err.data);
		});

		this.deleteCoupon = function(c) {

			var promiseDelete = mainCompanyService.deleteCoupon(c.id);
			promiseDelete.then(function(resp) {

				self.coupons = resp.data;

			}, function(err) {
				alert(err.data);
			});
		}

		// **************************** set table order /**********************
		// */

		this.orderB = "";
		this.goUp = false;

		this.setOrder = function(field) {
			this.goUp = (this.orderB != field) ? false : !this.goUp;
			this.orderB = field;
		}

	}

})();