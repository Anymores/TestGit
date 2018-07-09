/**
 * 初始化考勤详情对话框
 */
var LeaveInfoDlg = {
    LeaveInfoData : {}
};

/**
 * 清除数据
 */
LeaveInfoDlg.clearData = function() {
    this.LeaveInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
LeaveInfoDlg.set = function(key, val) {
    this.LeaveInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
LeaveInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
LeaveInfoDlg.close = function() {
    parent.layer.close(window.parent.Leave.layerIndex);
}

/**
 * 收集数据
 */
LeaveInfoDlg.collectData = function() {
    this.set('beginTime');this.set("remark");this.set("phone");this.set("id");this.set("context");this.set("lastTime");this.set("person")
}

/**
 * 提交添加
 */

LeaveInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/leave/add", function(data){
        Feng.success("添加成功!");
        window.parent.Leave.table.refresh();
        LeaveInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.LeaveInfoData);
    ajax.start();
}


/**
 * 审批通过
 */
LeaveInfoDlg.editYes = function() {


    if($("#state").html()=="管理员通过"){
        Feng.success("已经通过审核,请勿重复操作!");
        return;
    }

    if($("#person").val()==""||$("#person").val()==null){
        Feng.success("请签名!");
        return;
    }



    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/leave/sayyes", function(data){
        Feng.success("提交成功!");
        window.parent.Leave.table.refresh();
        LeaveInfoDlg.close();
    },function(data){
        Feng.error("提交失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.LeaveInfoData);
    ajax.start();
}

/**
 * 审核不通过
 */
LeaveInfoDlg.editNo = function () {

    if($("#state").html()=="管理员通过"){
        Feng.success("已经通过审核,请勿重复操作!");
        return;
    }

    if($("#person").val()==""||$("#person").val()==null){
        Feng.success("请签名!");
        return;
    }


    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/leave/sayno", function(data){
        Feng.success("提交成功!");
        window.parent.Leave.table.refresh();
        LeaveInfoDlg.close();
    },function(data){
        Feng.error("提交失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.LeaveInfoData);
    ajax.start();


};

/**
 * 跳转到自己的请假申请页面
 */
LeaveInfoDlg.showmeleave = function () {

    var index = layer.open({
        type: 2,
        title: '添加考勤',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/leave/showmeleave'
    });
    this.layerIndex = index;
}

/**
 * 删除自己的申请
 */

LeaveInfoDlg.delleave = function (id,pass) {
    if(pass=='1'){
        Feng.success("管理员正在审核,请勿删除!")
        return;
    }
    $.post("/leave/delete",{id:id},function (data) {
        Feng.success(data.success);
        window.location="/leave/showmeleave";
    },'json')
}


$(function() {

});
