sap.ui.define([
	'sap/ui/thirdparty/jquery',
	'sap/ui/core/mvc/Controller',
	'sap/ui/core/HTML'
], function(
	jQuery,
	Controller,
	HTML
) {
	"use strict";

	return Controller.extend("net.moewes.myUI5App.controller.Html", {

		onInit: function() {
			var oHtml = this.byId("htmlControl");

			if (!oHtml) {
				var sId = this.createId("htmlControl");
				oHtml = new HTML(sId, {
					// the static content as a long string literal
					content:
					"<div style='width=100%'><cloudui-view backend='http://localhost:8080/api/net.moewes.app.todo.TodoListView'></cloudui-view></div>",

					preferDOM : true,

					// use the afterRendering event for 2 purposes
					
				});

				var oLayout = this.byId("staticContentLayout");
				oLayout.addContent(oHtml);
			}
		}
    });
});