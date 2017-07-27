(function() {

	var module = angular.module("couponSystem");

	module.controller("GetAllCouponsCtrl", GetAllCouponsCtrlCtor);

	function GetAllCouponsCtrlCtor(mainCompanyService, $ngConfirm, $scope) {
		this.coupons = [];
		var self = this;
		this.show = false;
		// self.coupons.endDate = new Date();
		// var dateString = self.coupons.endDate.getDate() + "/"
		// + ("0" + (self.coupons.endDate.getMonth() + 1)).slice(-2) + "/"
		// + ("0" + self.coupons.endDate.getFullYear());

		// alert(dateString);

		var promiseGet = mainCompanyService.getAllCoupons();
		promiseGet.then(function(resp) {

			self.coupons = resp.data;

		}, function(err) {
			alert(err.data);
		});

		this.deleteCoupon = function(id) {

			$ngConfirm({
				title : 'Delete Coupon?',
				content : 'This dialog will automatically trigger \'cancel\'  if you don\'t respond.',
				autoClose : 'cancel|8000',
				buttons : {
					deleteUser : {
						text : 'delete Coupon',
						btnClass : 'btn-red',
						action : function(button) {
							mainCompanyService.deleteCoupon(id)
							$ngConfirm('Deleted...');
							location.reload();
						}
					},
					cancel : function() {
					}
				}
			});
		}

		this.showUpdateCoupon = function(c) {
			if (c.edit) {
				this.hideUpdateCoupon(c);
			} else {
				$('#successAlert').hide();
				$scope.today = new Date();
				// /========image edit handle============
				var fileSelect = document.createElement('input'); // input
				// it's
				// not
				// displayed in
				// html, I want to
				// trigger it form
				// other elements
				fileSelect.type = 'file';
				fileSelect.accept = "image/*";

				var r = new FileReader();
				r.onloadend = function(e) { // callback after files finish
					// loading
					c.image = e.target.result;
					$scope.$apply();
				}

				$scope.fileSelectClick = function() { // activate function to
					// begin
					// input file on click
					fileSelect.click();
				}

				fileSelect.onchange = function() { // set callback to action
					// after
					// choosing file
					if (fileSelect.files == null
							|| fileSelect.files == undefined) {
						alert("This Browser has no support for HTML5 FileReader yet! problem with Image upload");
						return false;
					}

					var f = fileSelect.files[0];
					c.image = f.name; // set imageName
					r.readAsDataURL(f); // once defined all callbacks, begin
					// reading
					// the
					// file
				};

				// drag N drop

				var dropzone = document.getElementById('dropzone');

				dropzone.ondrop = function(e) {
					e.preventDefault();
					this.className = 'dropzone w3-input w3-light-grey';

					if (e.dataTransfer.files == null
							|| e.dataTransfer.files == undefined) {
						alert("This Browser has no support for HTML5 FileReader yet!");
						return false;
					}

					var f = e.dataTransfer.files[0];
					self.coup.image = f.name; // set imageName
					r.readAsDataURL(f); // once defined all callbacks, begin
					// reading
					// the
					// file
				};

				dropzone.ondragover = function() {
					this.className = 'dropzone dragover w3-input w3-light-grey';
					return false
				};

				dropzone.ondragleave = function() {
					this.className = 'dropzone w3-input w3-light-grey';
					return false
				};
				c.edit = !c.edit;
			}
			this.show = true;
		}
		// ===============================================================================================
		// Hide update Coupon form and Update Coupon
		// ===============================================================================================
		this.hideUpdateCoupon = function(c) {
			var promisePost = mainCompanyService.updateCoupon(c);
			promisePost
					.then(
							function(resp) {
								$('#successAlert').show();
								$('#successAlert').text(
										"Coupon with id:" + c.id
												+ " updated successfuly");

							},
							function(err) {
								if (err.status == 401)
									window.location
											.replace("http://localhost:8080/WEB/HTMLs/index.html#/main");
								else
									$('#errorAlert').show();
								$('#errorAlert').text(err.data);
							});
			this.show = false;
			c.edit = !c.edit;
		}
		// *********************** set table order /********************** */

		this.orderB = "";
		this.goUp = false;

		this.setOrder = function(field) {
			self.goUp = self.goUp;
			self.orderB = field;
		}

	}

})();