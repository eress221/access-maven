$(function() {
    $.extend($.fn.dataTable.defaults, {
        deferRender: true,
        dom: "<'row mb-2'<'col-sm-12'rt>>" +
            "<'row'<'col-sm-12 col-md-6'l><'col-sm-12 col-md-6'f>>" +
            "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
        language: {
            url: contextPath + "/vendor/datatables/datatables.korean.json"
        },
        columnDefs: [
            {
                defaultContent: "",
                targets: "_all",
            },
            {
                defaultContent: 'null',
                targets: "_all"
            }
        ],
        initComplete: function(settings, json) {
            var $loading = $("#loading");
            if ($loading.is(":visible")) {
                $loading.hide();
            }
        },
    });
});

function adjustTable($target) {
    var $scroll = $target.closest(".dataTables_scroll");
    $scroll.find(".dataTables_scrollHeadInner, .dataTables_scrollHeadInner > table.dataTable").width($target.outerWidth());
    var $head = $scroll.find(".dataTables_scrollHead").find("thead").find("th");
    var $body = $scroll.find(".dataTables_scrollBody").find("thead").find("th");
    var total = $head.length;
    for (var i=0; i<total; i++) {
        $head.eq(i).outerWidth($body.eq(i).outerWidth());
    }

    var adjustResize = $target.data("adjustResize");
    if (!adjustResize) {
        $target.data("adjustResize", true);
        $(window).resize(function () {
            setTimeout(function() {
                adjustTable($target);
            }, 500);
        });
    }
}

function num(num) {
    var temp = 0;
    try {
        temp = Number(num);
        if(isNaN(temp)) {
            temp = Number(num.replace(/[^0-9]/g, ""));
        }
    } catch (e) {
        temp = 0;
    }
    return temp
}

// Add Comma
function addComma(num) {
    var pattern = /(-?[0-9]+)([0-9]{3})/;
    var n = String(num);
    while(pattern.test(n)) {
        n = n.replace(pattern, "$1,$2");
    }
    return n;
}

// Remove Comma
function removeComma(num) {
    return num.replace(/[^0-9]/g, "").replace(/,/gi, "");
}

// Left Padding
function leftPad(str, padLen, padStr) {
    if (padStr.length > padLen) {
        return str;
    }
    str += "";
    padStr += "";
    while (str.length < padLen)
        str = padStr + str;
    str = str.length >= padLen ? str.substring(0, padLen) : str;
    return str;
}

// Right Padding
function rightPad(str, padLen, padStr) {
    if (padStr.length > padLen) {
        return str + "";
    }
    str += "";
    padStr += "";
    while (str.length < padLen)
        str += padStr;
    str = str.length >= padLen ? str.substring(0, padLen) : str;
    return str;
}

// Chrome Check
function isChrome() {
    var agent = (navigator.userAgent ? navigator.userAgent : "").toUpperCase();
    var result = false;
    if(agent.indexOf("CHROME") > -1) {
        result = true;
    } else if(agent.indexOf("SAFARI") > -1) {
        result = false;
    } else if(agent.indexOf("FIREFOX") > -1) {
        result = false;
    }
    return result;
}