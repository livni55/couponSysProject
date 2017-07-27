(function() {

	var module = angular.module("couponSystem");

	module.controller("CreateCouponCtrl", CreateCouponCtrlCtor);

	// Ctor method for the CreateCouponCtrl
	// infoService, alertService, getCouponsService, constructors
	function CreateCouponCtrlCtor(mainCompanyService, $scope) {
		var self = this;
		$scope.today = new Date();
		this.success = false;
		this.failure = false;
		this.createCoupon = function() {
			if (self.coup == undefined) {
				alert("No coupon to add!");
				return;
			}
			if (self.coup.id == undefined) {
				alert("Must set coupon ID!");
				return;
			}
			if (self.coup.title == undefined) {
				alert("Must set coupon title!");
				return;
			}
			if (self.coup.startDate == undefined) {
				alert("Must set coupon start date!");
				return;
			}
			if (self.coup.endDate == undefined) {
				alert("Must set coupon end date!");
				return;
			}
			if (self.coup.amount == undefined) {
				alert("Must set coupon amount!");
				return;
			}
			if (self.coup.price == undefined) {
				alert("Must set coupon price!");
				return;
			}
			if (self.coup.image == undefined) {
				alert("Must set coupon image!");
				return;
			}
			if (self.coup.startDate.getTime() > self.coup.endDate.getTime()) {
				alert("Start date cannot be after end date! Fix that!");
				return;
			}
			if (new Date().getTime() > self.coup.endDate.getTime()) {
				alert("End date cannot be before today!");
				return;
			}
			var promise = mainCompanyService.createCoupon(self.coup);
			promise.then(function(resp) {
				self.coupons = resp.data;
				self.coup = {};
				self.success = true;
				self.failure = false;

			}, function(err) {
				self.success = false;
				self.failure = true;
			});
		}
		//
		// image upload handle
		//
		var fileSelect = document.createElement('input'); // input it's not
		// displayed in
		// html, I want to
		// trigger it form
		// other elements
		fileSelect.type = 'file';
		fileSelect.accept = "image/*";

		var r = new FileReader();
		r.onloadend = function(e) { // callback after files finish loading
			self.coup.image = e.target.result;
			$scope.$apply();
		}

		$scope.fileSelectClick = function() { // activate function to begin
			// input file on click
			fileSelect.click();
		}

		fileSelect.onchange = function() { // set callback to action after
			// choosing file
			if (fileSelect.files == null || fileSelect.files == undefined) {
				alert("This Browser has no support for HTML5 FileReader yet! problem with Image upload");
				return false;
			}

			var f = fileSelect.files[0];
			self.coup.image = f.name; // set imageName
			r.readAsDataURL(f); // once defined all callbacks, begin reading the
			// file
		};

		// drag N drop

		var dropzone = document.getElementById('dropzone');

		dropzone.ondrop = function(e) {
			e.preventDefault();

			if (e.dataTransfer.files == null
					|| e.dataTransfer.files == undefined) {
				alert("This Browser has no support for HTML5 FileReader yet!");
				return false;
			}

			var f = e.dataTransfer.files[0];
			self.coup.image = f.name; // set imageName
			r.readAsDataURL(f); // once defined all callbacks, begin reading the
			// file
		};

		dropzone.ondragover = function() {
			return false
		};

		dropzone.ondragleave = function() {
			return false
		};
	}

})();