$(function() {
    $("#book-table").dataTable({
        language: {
            url: "/webjars/datatables-plugins/i18n/Japanese.json"
        },
        "order": [],
        dom: "Bfrtip",
        buttons: [],
        //buttons: ["excelHtml5", "csvHtml5", "print"]
        columnDefs: [
	        { "orderable": false, "targets": 6},
	    ]
    });
});
