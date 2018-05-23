/** Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

/**
 * Created by kavithakannan on 2/21/18.
 */
(function($) {


  $.fn.serialize = function(options) {
    return $.param(this.serializeArray(options));
  };

  $.fn.serializeArray = function(options) {
    var o = $.extend({
      checkboxesAsBools: false
    }, options || {});

    var rselectTextarea = /select|textarea/i;
    var rinput = /text|hidden|password|search/i;

    return this.map(function() {
      return this.elements
        ? $.makeArray(this.elements)
        : this;
    }).filter(function() {
      return this.name && !this.disabled && (this.checked || (o.checkboxesAsBools && this.type === 'checkbox') || rselectTextarea.test(this.nodeName) || rinput.test(this.type));
    }).map(function(i, elem) {
      var val = $(this).val();
      return val == null
        ? null
        : $.isArray(val)
          ? $.map(val, function(val, i) {
            return {name: elem.name, value: val};
          })
          : {
            name: elem.name,
            value: (o.checkboxesAsBools && this.type === 'checkbox')
              ? //moar ternaries!
              (
                this.checked
                ? 'true'
                : 'false')
              : val
          };
    }).get();
  };

  var utils = {
    sumArray: function(arr) {
      var sum = 0,
        lg = arr.length;
      for (var i = 0; i < lg; i += 1) {
        sum += arr[i];
      }
      return sum;
    }

  }

  //custom validate plugin of jquery

  var validates = { //now only have required validate
    requiredValue: function(value) {
      return value
        ? 0
        : 1;
    },
    required: function(opt) {
      var value = opt.value,
        errorDom = opt.errorDom,
        parent = opt.parent;
      if (!value) {
        errorDom.show().text('This field is required');
        parent.addClass('has-error');
      } else {
        errorDom.hide();
        parent.removeClass("has-error");
      }
      return this.requiredValue(value);
    }
  }

  var validateEvent = function(form) { //demo,only use fixed html structure,if extend ,maybe add data-parent/data-error
    var formValidateStore = []; //maybe extend to store input message.
    return {
      event: function(e) {
        var ts = $(this);
        var validate = ts.data('validate').split(','),
          vid = + ts.data('vid');
        var key = validate[0],
          value = validate.slice(1).join(',');
        if (!key) {
          return;
        }
        var ind = 0;
        if (isNaN(vid)) {
          formValidateStore.push(true);
          ind = formValidateStore.length - 1,
          ts.data('vid', ind);
        }
        //dom handle
        var parent = ts.parent();
        var errorDom = parent.find('.error-notice');
        var tf = validates[key]({errorDom: errorDom, value: ts.val(), parent: parent});
        formValidateStore[vid] = tf;
        var sum = utils.sumArray(formValidateStore);
        console.warn(formValidateStore)
        if (sum === 0) {
          form.find(form.data('submit')).removeAttr('disabled');
        } else {
          form.find(form.data('submit')).attr('disabled', true);
        }
      },
      init: function(form, doms) {
        doms.each(function(ind, dm) {
          dm = $(dm);
          var validate = dm.data('validate').split(',');
          var key = validate[0],
            value = validate.slice(1).join(',');
          dm = $(dm);
          var val = dm.val();
          dm.data('vid', ind);
          formValidateStore[ind] = validates[key + 'Value'](val, value);
        });
        var sum = utils.sumArray(formValidateStore);
        if (sum === 0) {
          form.find(form.data('submit')).removeAttr('disabled');
        } else {
          form.find(form.data('submit')).attr('disabled', true);
        }
      }
    };
  };


  $.fn.inputValidate = function() {
    this.each(function(ind, dom) {
      dom = $(dom);
      var validateObj = validateEvent(dom);
      var eles = dom.find('[data-validate]');
      validateObj.init(dom, eles);
      eles.on('keyup', validateObj.event);
    });
  };
  //custom validate plugin of jquery end

  $.fn.buttonLoading = function(tf) {
    var ts = this;
    tf = tf === true
      ? true
      : false;
    if (tf === false) {
      ts.removeAttr('disabled').find('.loader').remove();
    } else {
      ts.attr('disabled', true).append($('<span class="loader loader-quart"></span>'));
    }
  };

  $.messageNotice = {
    clear: function() {
      this.ids.forEach(function(id) {
        clearTimeout(id);
      });
      this.ids = [];
    },
    ids: [],
    hideTime: 3600,
    success: function(msg) {
      if (typeof msg === "object") {
        msg = JSON.stringify(msg);
      }
      var time = this.hideTime;
      this.clear();
      $(".pop-message").hide();
      $('.pop-message-success').fadeIn().find('.message').text(msg);
      this.ids.push(setTimeout(function() {
        $('.pop-message-success').hide();
      }, time));
    },
    fail: function(msg) {
      if (typeof msg === "object") {
        msg = JSON.stringify(msg);
      }
      var time = this.hideTime;
      this.clear();
      $(".pop-message").hide();
      $('.pop-message-fail').fadeIn().find('.message').text(msg);
      this.ids.push(setTimeout(function() {
        $('.pop-message-fail').hide();
      }, time));
    }
  };

})(jQuery);



$(function() {
  //twitter bootstrap script
  canUserConfigure = function() {
    console.log("In canUserConfigure");
    console.log(localStorage.token);
    console.log(localStorage.isAdmin);
    if(localStorage.isAdmin != null && localStorage.isAdmin != undefined){
        admin = JSON.parse(localStorage.isAdmin);
        user = !JSON.parse(localStorage.isAdmin);
    }
  };

  canUserConfigure();

  $("#openGuidance").on("click",function(){
    $("#intruduce").show();
    $("body").css('overflowY','hidden');
  });


$(".close").on("click",function(){
  var ts=$(this);
  var aim=ts.data('aim');
  $(aim).hide();
  $("body").css('overflowY','auto');

});

  $("button#panoptosubmit").click(function() {
    var formdata = $('#adminform').serialize({checkboxesAsBools: true});
    formdata = formdata + "&" + "token=" + localStorage.token;
    var btn = $(this);
    btn.buttonLoading(true);
    $.ajax({
      type: "POST",
      url: "/api/v1/kaltura/saveConfig",
      data: formdata,
      success: function(msg) {
        $.messageNotice.success(msg);
        btn.buttonLoading(false);
      },
      error: function() {
        $.messageNotice.fail('failure');
        btn.buttonLoading(false);
      }
    });
  });

  $("button#kalturaUser").click(function() {
    var formdata = $('#userform').serialize({checkboxesAsBools: true});
    formdata = formdata + "&" + "token=" + localStorage.token ;
    var btn = $(this);
    btn.buttonLoading(true);
    $.ajax({
      type: "POST",
      url: "/api/v1/kaltura/user/saveConfig",
      data: formdata,
      success: function(msg) {
        btn.buttonLoading(false);
        $.messageNotice.success(msg);

      },
      error: function() {
        btn.buttonLoading(false);
        $.messageNotice.fail('failure');
      }
    });
  });

});

$(document).ready(function() {

  // after the page elements are all loaded, then run the script
  // Set the input field with unique ID #email to a value
  showHideForm = function(admin, user) {
    console.log(admin);
    console.log(user);

    if (admin) {
      $('#adminform').show();
      $('#userform').hide();
    } else if (user) {
      $('#adminform').hide();
      $('#userform').show();
    } else {
      $('#adminform').hide();
      $('#userform').hide();
    }
  };
  showHideForm(admin, user);

  if (JSON.parse(admin)) {
    $.ajax({
      type: "GET",
      url: "/api/v1/kaltura/fetch" + "?" + "token=" + localStorage.token,
      dataType: "json",
      success: function(kalconfig) {
        $("#username").val(kalconfig.userName);

        // Set the input field with unique ID #name

        $("#adminUserid").val(kalconfig.userId);
        $("#userIdMsg").text(kalconfig.userId);
        $("#partnerid").val(kalconfig.partnerId);
        $("#administratorsecret").val(kalconfig.administratorSecret);

        //checkbox value
        $("#enableupload").val(kalconfig.enableUpload);
        $("#category").val(kalconfig.category);

        //checkbox checked
        if (parseInt(kalconfig.enableUpload) == 1) {
          $('#enableupload').attr('checked', true);
        } else {
          $('#enableupload').attr('checked', false);
        }

        if (parseInt(kalconfig.categoryByZoomRecording) == 1) {
          $('#category').attr('checked', true);
        } else {
          $('#category').attr('checked', false);
        }

        $('form').inputValidate(); //begin check input error message
      },
      error: function() {
        $.messageNotice.fail('failure');
      }
    });
  } else if (JSON.parse(user)) {
    $.ajax({
      type: "GET",
      url: "/api/v1/kaltura/user/fetch" + "?" + "token=" + localStorage.token,
      success: function(kalconfig) {
        $("#userid").val(kalconfig.userConfigId);
        $("#userIdMsg").text(kalconfig.userConfigId);//if insert userConfigId
      },
      error: function() {
        $.messageNotice.fail('failure');
      }
    });
  }

});
