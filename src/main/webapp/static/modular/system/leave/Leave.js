/**
 * 考勤管理初始化
 */
var Leave = {
    id: "LeaveTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptid:0
};

/**
 * 初始化表格的列
 */
Leave.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '申请人', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '开始时间', field: 'startTime', align: 'center', valign: 'middle', sortable: true},
        {title: '结束时间', field: 'endTime', align: 'center', valign: 'middle', sortable: true},
        {title: '请假原因', field: 'remark', align: 'center', valign: 'middle', sortable: true},
        {title: '审核状态', field: 'pass', align: 'center', valign: 'middle', sortable: true}
]
       return columns;
};

/**
 * 检查是否选中
 */
Leave.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Leave.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加考勤
 */
Leave.openAddLeave = function () {
    var index = layer.open({
        type: 2,
        title: '添加考勤',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/leave/showSheet'
    });
    this.layerIndex = index;
};

/**
 * 打开查看考勤详情
 */
Leave.openLeaveDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '考勤详情',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/leave/Leave_update/' + Leave.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除考勤
 */
Leave.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/leave/delete", function (data) {
            Feng.success("删除成功!");
            Leave.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("LeaveId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查案审批详情
 */

Leave.showleave=function () {

    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '考勤详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/leave/showleave/' + Leave.seItem.id
        });
        this.layerIndex = index;
    }

}

/**
 * 查询考勤列表
 */
Leave.search = function () {

    var queryData = {};
    queryData['autoJSON'] = $("#autoJSON").val();
    Leave.table.refresh({query: queryData});
};

/**
 * 自动补全
 */
Leave.autoComplete = function () {

    $("#message").html("");
    if($("#autoJSON").val()==null || $("#autoJSON").val()==""){
        return;
    }

    $.post("/leave/getjson",{name:$("#autoJSON").val()},function (data) {
        var message=$("#message");
        message.html("");
        if(data.length>0){
            for(var i = 0;i<data.length;i++){
                var $div = $("<div>"+data[i]+"</div>");
                $div.click(function () {
                    $("#autoJSON").val($(this).html());
                    message.hide();
                    Leave.search();
                })
                message.append($div)
            }
            message.show();
        }else{
            var $div = $("<div>抱歉,暂无数据!</div>");
            message.append($div);
            $div.click(function () {
                message.hide();
            })

        }
    },'json')

}


$(function () {
    var defaultColunms = Leave.initColumn();
    var table = new BSTable("LeaveTable", "/leave/list", defaultColunms);
    table.setPaginationType("client");
    Leave.table = table.init();
});
