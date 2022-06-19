sap.ui.define([
	"rcAdminApp/controller/BaseController"
], function (BaseController) {
	"use strict";
	return BaseController.extend("rcAdminApp.controller.VeranstaltungView", {
		onInit: function () {
			var oRouter = this.getRouter();
			oRouter.getRoute("veranstaltung").attachMatched(this._onRouteMatched, this);
		},
		_onRouteMatched : function (oEvent) {
			var oArgs, oView;
			oArgs = oEvent.getParameter("arguments");
			oView = this.getView();

			oView.bindElement({
				path : "/Veranstaltungen('" + oArgs.id + "')",
				events : {
					change: this._onBindingChange.bind(this),
					dataRequested: function (oEvent) {
						oView.setBusy(true);
					},
					dataReceived: function (oEvent) {
						oView.setBusy(false);
					}
				}
			});
		},
		_onBindingChange : function (oEvent) {
			// No data for the binding
			if (!this.getView().getBindingContext()) {
				this.getRouter().getTargets().display("notFound");
			}
		}
	});
});