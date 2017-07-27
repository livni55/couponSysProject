(function () {

	var module = angular.module("couponSystem");

	module.controller("GetCouponByDateCtrl", GetCouponByDateCtrlCtor);

	function GetCouponByDateCtrlCtor(mainCompanyService) {
		this.couponsDate = [];
		var self = this;
		
		// convert Date to String
//		var day = this.endDate.getDate() < 10 ? '0' + this.endDate.getDate() : this.endDate.getDate();
//		var month = (this.endDate.getMonth() + 1) < 10 ? '0' + (this.endDate.getMonth() + 1) : (this.endDate.getMonth() + 1);
//		this.endDate = day + "/" + month + "/" + this.endDate.getFullYear();

		this.getDate = function () {
			var promiseGet = mainCompanyService.getCouponsByDate(this.endDate.getTime());

				promiseGet.then(
					function (resp) {
						
						self.couponsDate = resp.data;
					},
	
					function (err) {
						alert(err.data);
					});

			//******************************** set table order /************************************* */
				
			this.orderB = "";
			this.goUp = false;

			this.setOrder = function (field) {
				this.goUp = (this.orderB != field) ? false
					: !this.goUp;
				this.orderB = field;
			}

		}
	}

})();