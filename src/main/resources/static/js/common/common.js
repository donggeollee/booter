$(function(){
    $("title").html('[' + $("meta[name=environment]").attr("content") + '] OPERATION SYSTEM');

    $.ajaxSetup({
        cache: false,
        beforeSend: function(xhr){
            Loader.show();
            if( window.idToken ){
                xhr.setRequestHeader('X-Authorization-Firebase', window.idToken);
            }
        },
        complete: function(){
            if( !LoaderIsWait ){
                Loader.hide();
            }
        },
        error: function(xhr, timeout, message){
            console.error("ajaxSetup error", xhr);
            if( xhr.status === 403 ){
                swal('권한이 없습니다.', '', 'warning');
            }
        }

    });

    // 달력 입력 항목은 자동 완성 끄기
    $(".datepicker, .datetimepicker").attr("autocomplete", "off");

    // SMS 전송 막기
    setTimeout(function(){
        $("button[id^=SendSMSModalOpen], button[id^=SendSMSOpen]").off('click');
        $("button[id^=SendSMSModalOpen], button[id^=SendSMSOpen]").click(wait);
    }, 500);

    $(document).on('hidden.bs.modal', '[role="dialog"]', function(){
        var $this = $(this);

        // dataTable 제외
        if( $this.find('#dataTableFilter').length > 0 || $this.attr("id") == 'dataTableFilter' )
            return;

        // data-no-reset 제외
        if( $this.attr('data-no-reset') != null )
            return;

        $this.find("input[type='text'], textarea").val("");
        for(var i=0; i<$this.find("textarea").length; i++){
            try {
                $this.find("textarea")[i]['data-froala.editor'].html.set("")
            } catch (e) {}
        }

        $this.find("input[type=file]").each(function(){
            var agent = navigator.userAgent.toLowerCase();
            if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
                $(this).replaceWith( $(this).clone(true) );
            } else {
                $(this).val("");
            }
            $(this).siblings("label").text("");
        });

        // vuejs 수동 처리
        var id = $this.attr('id');
        switch (id) {
            case 'GradeModal':
                if( window.gradeModalApp ){
                    window.gradeModalApp.contents = '';
                    window.gradeModalApp.level = '';
                }
                break;
        }

        console.warn('hidden.bs.modal', $this);
    });
});

// 패스워드 입력창
$.fn.passwordInput = function(){
    $(this).each(function(){
        var $inputGroup = $("<div class='input-group password-view'/>");
        var $eye = $('<i class="fa fa-eye-slash fa-lg pointer"></i>')
            .css({
                position: 'absolute',
                top: '50%',
                right: 10,
                zIndex: 5,
                display: 'none'
            })
            .click(function(){
                if( $(this).hasClass('fa-eye-slash') ) {
                    $(this).removeClass('fa-eye-slash').addClass('fa-eye');
                    $input.attr('type', 'text');
                } else {
                    $(this).removeClass('fa-eye').addClass('fa-eye-slash');
                    $input.attr('type', 'password');
                }
            });
        var $input = $(this)
            .css({
                paddingRight: 40
            })
            .keyup(function(){
                if( this.value.length > 0 ){
                    $eye.show();
                } else {
                    $eye.hide();
                }
            });
        $input.parent().append($inputGroup);
        $inputGroup.append($input, $eye);

    });
};

var Pos = {
    initDataTable: function(dt, callback){
        if( callback == null ) return;
        callback(dt);
        $(".datepicker").datepicker({
            format: "yyyy-mm-dd",
            clearBtn: true,
            todayHighlight: true,
            autoclose: true
        });

        $("#dataTable_wrapper input[type=search]").unbind();
        $("#dataTable_wrapper input[type=search]").keydown(function(e){
            if( e.keyCode == 13 ){
                dt.search( this.value ).draw();
            }
        });
    },
    logout: function(){
        swal({
            title: '로그아웃 하시겠습니까?',
            text: "",
            type: 'warning',
            showCancelButton: true,
            confirmButtonText: '네',
            cancelButtonText: '아니오'
        }).then(function(result) {
            if (result.value) {
                Cookies.remove('idToken', {
                    path: '/'
                });
                firebaseEmailAuth.signOut();
                window.location.href = "/pos";
            }
        });
    },
    supportLanguage: function(type){
        var langs = ["en_US","ko_KR","ja_JP","zh_CN","zh_TW","zh_HK","th_TH","vi_VN"];
        if( type == 'object' ){
            var obj = {};
            for(var i in langs) {
                obj[langs[i]] = '';
            }
            return obj;

        } else {
            return langs;
        }
    },
    getLanguageName: function(lang){
        // var langs = ["영어","한국어","일본어","중국어 간체(중국)","중국어 번체(대만)","중국어 번체(홍콩)","태국어","베트남어"];
        var langs = ["English","Korean","Japanese","Chinese","Chinese(TW)","Chinese(HK)","Thai","Vietnamese"];
        return langs[ Pos.supportLanguage().indexOf(lang) ];
    },
    setPosIdToken: function(idToken, callback){
        $.post("/pos/parseIdToken", {idToken: idToken}, function(res){
            console.log(res);
            if( res.result ){
                var expires = moment(res.data.claims.exp * 1000).toDate();
                console.log(expires);

                Cookies.set('idToken', idToken, {
                    expires: expires,
                    path: '/'
                });
            }

            if( callback ){
                callback();
            }
        });
    },
    getPosIdToken: function(){
        var idToken = Cookies.get('idToken');
        if( idToken ){
            return idToken;
        } else {
            firebase.auth().currentUser.getIdToken(/* forceRefresh */ true).then(function(idToken) {
                return idToken;
            }).catch(function(error) {
                console.log('idTokenError :: ');
            });

        }
    },
    getServerPort: function(){
        var port = document.location.port;
        if( !port || port == 80 ) port = '';
        else port = ':' + port;
        return port;
    },
    getPosUrl: function(path){
        return '//' + document.location.hostname + Pos.getServerPort() + "/pos" + path;
    },
    getPosApiUrl: function(path){
        return '//' + document.location.hostname + Pos.getServerPort() + "/pos/api" + path;
    },
    getApiUrl: function(path){
        return '//' + document.location.hostname + Pos.getServerPort() + "/api" + path;
    },
    jsonToForm: function($form, data){
        _.each(Object.keys(data), function(key){
            var selector = "*[name='" + key + "']";
            var val = data[key];
            // console.log(selector, val);

            if( typeof val === 'object' ){
                _.each(Object.keys(data))

            } else {
                $form.find(selector).val( data[key] );
            }
        });
    },
    getUrlParams: function(){
        var params = {};

        window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) { params[key] = value; });

        return params;
    }

};

var LoaderIsWait = false;
var Loader = {
    show: function(isWait){
        if( isWait )
            LoaderIsWait = isWait;
        if( !Loader.isShow() )
            $(".wrap-loading").fadeIn('fast');
    },
    hide: function(isWait){
        $(".wrap-loading .text").hide();
        $(".wrap-loading").fadeOut('fast');
        if( isWait )
            LoaderIsWait = false;
    },
    isShow: function(){
        return $(".wrap-loading").is(":visible");
    },
    setText: function(text){
        $(".wrap-loading .text").show();
        $(".wrap-loading .text").html(text);
    }
};

function numberOnly(e){
    e.target.value = e.target.value.replace(/[^0-9\.]/g,'');
}

function phoneOnly(e){
    e.target.value = e.target.value.replace(/[^0-9+\- \.]/g,'');
}

function readURL(input, callback) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function(e) {
            callback(e.target.result);
        };

        reader.readAsDataURL(input.files[0]);
    }
}

/**
 * DataTable checkbox 선택 항목 데이터
 * @param dataTable
 * @param returnType (id, data)
 * @returns {Array}
 */
function getDatatableCheckedData(dataTable, returnType){
    var $list = $('.dataTable-cb');
    if( $list.length == 0 ){
        $list = $("#dataTable_wrapper input[type='checkbox']");
    }
    $list = $list.not('[value=""]');
    $list = $list.not('.all-check');
    var ids = $list.map(function() {
        if (this.checked) {
            return $(this).val();
        }
    }).get();

    var selectedList = _.filter( dataTable.data(), function(o){
        return ( ids.indexOf(o.id) > -1 );
    });

    if( returnType == 'id' ){
        return _.map(selectedList, function(o){
            return o.id;
        });
    } else {
        return selectedList;
    }
}

Number.prototype.numberFormat = function(currency){
    if( !this ) return 0;
    var num = this;
    if( typeof num === "string" ) num = Number(num);
    if( isNaN(num) ) return 0;

    var reg = /(^[+-]?\d+)(\d{3})/;

    var n = (this + '');
    if( currency == 'usd' ){
        n = (this / 100) + '';
    }

    while (reg.test(n)) n = n.replace(reg, '$1' + ',' + '$2');

    return n;
};

String.prototype.numberFormat = function(currency){
    if( !this ) return 0;
    var num = this;
    if( typeof num === "string" ) num = Number(num);
    if( isNaN(num) ) return 0;

    var reg = /(^[+-]?\d+)(\d{3})/;
    var n = (this + '');
    if( currency == 'usd' ){
        n = (this / 100) + '';
    }

    while (reg.test(n)) n = n.replace(reg, '$1' + ',' + '$2');

    return n;
};

/**
 * 다국어 입력 필드 처리
 * @param $list
 * @param options
 *  selected: ['ko']
 *  type: 'summernote', 'text'
 */
var editors = {};
function toggleLanguageField($target, options){
    if( !options ){
        options = {};
    }

    options.selected = options.selected || ['ko'];

    $target.each(function(){
        if( $(this).data('editType') ){
            options.type = $(this).data('editType');
        }

        // console.log(options, $(this));

        if( options.type == 'text' ){
            $(this).find("span").each(function(){
                var $btn = $(this);
                var lang = $btn.data("lang");
                var fieldName = $btn.parent().data("langField");
                $btn.click(function(){
                    var $fieldRow = $("*[class*='lang-" + lang + "'][data-lang-field='" + fieldName + "']");
                    if( $btn.hasClass("badge-secondary") ){
                        $fieldRow.show();
                        $btn.removeClass("badge-secondary");
                        $btn.addClass("badge-primary");
                    } else {
                        $fieldRow.hide();
                        $btn.removeClass("badge-primary");
                        $btn.addClass("badge-secondary");
                    }
                });
                if( options ){
                    if( options.selected.indexOf(lang) > -1 ){
                        $btn.attr('data-lang-default', true);
                        $btn.click();
                    }
                }
            });
        }

        if( options.type == 'froala' ){
            var subPath = options.subPath;
            if( subPath.substring(0, 1) == '/' ){
                subPath = subPath.substring(1, subPath.length);
            }
            var editorConfig = {
                key: froala_key,
                attribution: false,
                charCounterCount: false,
                height:300,
                fontFamily: false,
                fontFamilySelection: false,
                fontSize: ['8', '9', '10', '11', '12', '14', '15', '16', '17', '18', '19', '20', '22', '24', '26', '30', '36', '48', '60'],
                fontSizeDefaultSelection: '17',
                videoUploadParam: 'file',
                videoUploadURL: '/pos/common/upload/froala?subPath=' + subPath,
                imageUploadParam: 'file',
                imageUploadURL: '/pos/common/upload/froala?subPath=' + subPath,
                imageUploadMethod: 'POST',
                imageAllowedTypes: [
                    'jpeg',
                    'jpg',
                    'png',
                    'git',
                ],
                toolbarButtons: {
                    'moreText': {
                        'buttons': ['bold', 'italic', 'underline', 'strikeThrough', 'subscript', 'superscript', /*'fontFamily',*/ 'fontSize', 'textColor', 'backgroundColor', 'inlineClass', 'inlineStyle', 'clearFormatting']
                    },
                    'moreParagraph': {
                        'buttons': ['alignLeft', 'alignCenter', 'formatOLSimple', 'alignRight', 'alignJustify', 'formatOL', 'formatUL', 'paragraphFormat', 'paragraphStyle', 'lineHeight', 'outdent', 'indent', 'quote']
                    },
                    'moreRich': {
                        'buttons': ['insertLink', 'insertImage', 'insertVideo', 'embedly', 'insertTable', 'emoticons', 'fontAwesome', 'specialCharacters', 'insertFile', 'insertHR'],
                        'buttonsVisible': 4
                    },
                    'moreMisc': {
                        'buttons': ['undo', 'redo', 'fullscreen', 'print', /*'getPDF'*/, 'spellChecker', 'selectAll', 'html', 'help'],
                        'align': 'right',
                        'buttonsVisible': 2
                    }
                },
                enter: FroalaEditor.ENTER_DIV,
                events: {
                    // 'blur': function(e){
                    //     var $langRow = $(e.target).closest("div[class*=lang-]");
                    //     var $editor = $langRow.find('.froala_edit');
                    //     var dataField = $langRow.data("langField");
                    //     var lang = $editor.data('name');
                    //     options.data[dataField][lang] = this.html.get();
                    //     console.log( dataField, lang, this.html.get() );
                    // },
                    'contentChanged': function(e){
                        $langRow = $(this.$el).closest("div[class*=lang-]");
                        var $editor = $langRow.find('.froala_edit');
                        var dataField = $langRow.data("langField");
                        var lang = $editor.data('name') || $editor.attr('name');

                        var $html = $("<div/>").html($(this.html.get()));

                        // 비디오 썸네일 보이게
                        $html.find('video').each(function(){
                            var src = $(this).attr('src');
                            var arr = src.split('#');
                            if( arr.length == 1 || (arr.length > 1 && arr[1] != 't=0.1') ){
                                $(this).attr('src', src + '#t=0.1');
                            }
                        });

                        // youtube 영상 등록시
                        $html.find('iframe').each(function(){
                            var $parent = $(this).parent();
                            var $iframe = $(this);
                            if( $iframe.attr('src').indexOf('youtube.com') > -1 ){
                                // $(this).remove();
                                // var $video = $('<div class="video-container"/>');
                                // $video.append($iframe);
                                // $parent.append($video);
                                $(this).closest('div').addClass('video-container');
                            }
                        });

                        var html = $html.html();

                        options.data[dataField][lang] = html;
                        console.log( dataField, lang, html );
                    }
                }
            };

            $(this).find("span").each(function(){
                var $btn = $(this);
                var lang = $btn.data("lang");
                var fieldName = $btn.parent().data("langField");
                var editorName = fieldName + '_' + lang;
                $btn.click(function(){
                    var $fieldRow = $("*[class*='lang-" + lang + "'][data-lang-field='" + fieldName + "']");
                    var $textArea = $fieldRow.find('.froala_edit');

                    if( $btn.hasClass("badge-secondary") ){
                        $fieldRow.show();
                        $btn.removeClass("badge-secondary");
                        $btn.addClass("badge-primary");

                        if( !editors[editorName] ){
                            editors[editorName] = new FroalaEditor($textArea[0], editorConfig);
                        }

                    } else {
                        $fieldRow.hide();
                        $btn.removeClass("badge-primary");
                        $btn.addClass("badge-secondary");

                        if( editors[editorName] ){
                            editors[editorName].destroy();
                            editors[editorName] = null;
                        }
                    }
                });
                if( options ){
                    if( options.selected.indexOf(lang) > -1 ){
                        $btn.attr('data-lang-default', true);
                        $btn.click();
                    }
                }
            });
        }

        if( options.type == 'summernote' ){
            var summernoteOptions = {
                height: 300,
                minHeight: null,
                maxHeight: null,
                focus: false,
                lang: 'ko-KR',
                toolbar: [
                    ['style', ['style']],
                    ['font', ['bold', 'underline', 'clear', 'del', 'strikethrough', 'fontsize']],
                    ['fontname', ['fontname']],
                    ['color', ['color']],
                    ['para', ['ul', 'ol', 'paragraph']],
                    ['table', ['table']],
                    ['insert', ['link', 'picture', 'video']],
                    ['view', ['fullscreen', 'codeview', 'help']],
                ],
                callbacks: {
                    onImageUpload: function(files, editor, welEditable) {
                        for (var i = files.length - 1; i >= 0; i--) {
                            uploadSummernote(options.subPath, files[i], this);
                        }
                    },
                    // onChange: function(contents, $editable) {
                    //     var langRow = $(this).closest("div[class*=lang-]");
                    //     var name = langRow.data("langField");
                    //     options.data[name][$(this).attr('name')] = contents;
                    // },
                    onBlur: function(e) {
                        var $langRow = $(e.target).closest("div[class*=lang-]");
                        var $summernote = $langRow.find('.summernote');
                        var dataField = $langRow.data("langField");
                        var lang = $summernote.attr('name');
                        options.data[dataField][lang] = $summernote.summernote('code');
                        console.log(dataField + '.' + lang, $summernote.summernote('code'));
                    },
                    onPaste: function (e) {
                        var bufferText = ((e.originalEvent || e).clipboardData || window.clipboardData).getData('Text');

                        e.preventDefault();

                        setTimeout( function(){
                            document.execCommand( 'insertText', false, bufferText );
                        }, 10 );
                    }
                }
            };

            $(this).find("span").each(function(){
                var $btn = $(this);
                var lang = $btn.data("lang");
                var fieldName = $btn.parent().data("langField");
                $btn.click(function(){
                    var $fieldRow = $("*[class*='lang-" + lang + "'][data-lang-field='" + fieldName + "']");
                    var $textArea = $fieldRow.find('.summernote');
                    // var $textArea = $fieldRow.find('textarea');
                    // if( $btn.hasClass("badge-secondary") ){
                    //     $fieldRow.show();
                    //     $btn.removeClass("badge-secondary");
                    //     $btn.addClass("badge-primary");
                    //     $textArea.summernote(summernoteOptions);
                    // } else {
                    //     $fieldRow.hide();
                    //     $btn.removeClass("badge-primary");
                    //     $btn.addClass("badge-secondary");
                    //     $textArea.summernote('destroy');
                    // }
                    if( $btn.hasClass("badge-secondary") ){
                        $fieldRow.show();
                        $btn.removeClass("badge-secondary");
                        $btn.addClass("badge-primary");

                        if ($textArea.next('.note-editor').length === 0) {
                            $textArea.summernote(summernoteOptions);
                        }
                    } else {
                        $fieldRow.hide();
                        $btn.removeClass("badge-primary");
                        $btn.addClass("badge-secondary");
                        // $textArea.summernote('destroy');
                    }
                });
                if( options ){
                    if( options.selected.indexOf(lang) > -1 ){
                        $btn.attr('data-lang-default', true);
                        $btn.click();
                    }
                }
            });
        }

    });
}

function uploadSummernote(subPath, file, el) {
    var form_data = new FormData();
    form_data.append('subPath', subPath);
    form_data.append('file', file);
    $.ajax({
        data: form_data,
        type: "POST",
        url: '/pos/common/upload/summernote',
        cache: false,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function(data) {
            $(el).summernote('editor.insertImage', data.link);
            $('#contents > ul').append('<p><img src="'+data.link +'"/></p>');
        }
    });
}

function getDefaultSummernoteOptions(subPath){
    return {
        height: 300,
        minHeight: null,
        maxHeight: null,
        focus: true,
        callbacks: {
            onImageUpload: function(files, editor, welEditable) {
                for (var i = files.length - 1; i >= 0; i--) {
                    uploadSummernote(subPath, files[i], this);
                }
            }
        }
    }
}

function initSumernote(target, subPath){
    if( !subPath ){
        alert('summernote required upload subPath');
        return false;
    }
    target.summernote(getDefaultSummernoteOptions(subPath));
}

var ObjectId = function () {
    var timestamp = (new Date().getTime() / 1000 | 0).toString(16);
    return timestamp + 'xxxxxxxxxxxxxxxx'.replace(/[x]/g, function() {
        return (Math.random() * 16 | 0).toString(16);
    }).toLowerCase();
};

/**
 * 처리 내용 저장
 * @param data
 *  title
 *  contents
 *  file: jquery.file
 *  targetDocument
 *  targetId
 *  description
 */
function saveAdminWork(data, callback){
    data.pathname = location.pathname;
    var formData = new FormData();
    _.each(Object.keys(data), function(key){
        if( key != 'file' )
            formData.append(key, data[key]);
    });

    if( data.file && data.file[0].files[0] ){
        console.log("uploadWorkFile", data.file[0].files[0]);
        formData.append("uploadWorkFile", data.file[0].files[0]);
    }

    $.ajax({
        data: formData,
        type: "POST",
        url: Pos.getPosApiUrl('/adminWork'),
        cache: false,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function(res) {
            if( callback ){
                callback(res);
            }
        }
    });
    console.log(formData);
}

/**
 * 처리 내용 저장
 * @param data
 *  title
 *  contents
 *  file: jquery.file
 *  targetDocument
 *  targetId
 *  description
 */
function saveActivityLog(data, callback){
    $.ajax({
        data: JSON.stringify(data),
        type: "POST",
        url: Pos.getPosApiUrl('/activityLog'),
        dataType: 'json',
        contentType: 'application/json',
        success: function(res) {
            if( callback ){
                callback(res);
            }
        }
    });
}

function uploadFile($target, subPath, callback){
    if( $target.length == 0 || $target.val() == '' ) {
        callback({});
        return;
    };

    var file = $target;
    var formData = new FormData();
    formData.append("file", file[0].files[0]);
    formData.append("subPath", subPath);

    $.ajax({
        data: formData,
        type: "POST",
        url: Pos.getPosUrl('/common/upload/froala'),
        cache: false,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function(res) {
            if( callback ){
                callback(res);
            }
        }
    });
}

function isScrollEnd(target){
    var st = $(target)[0].scrollHeight - $(target).scrollTop();
    st = st/2;
    var h = $(target).outerHeight();
    var is = st <= h;
    // console.log(is, st, h);
    return is;
}

function wait(){
    try {
        var event = window.event || arguments.callee.caller.arguments[0];
        event.preventDefault();
        event.stopPropagation();
    } catch (e) {}
    swal('준비중 입니다.', '', 'warning');
}

function dollarFormat(val){
    if( typeof(val) == 'string' ){
        val = Number(val);
    }
}

function isPasswordValid(val){
    var pattern1 = /[0-9]/;
    var pattern2 = /[a-zA-Z]/;
    var pattern3 = /[~!@\#$%<>^&*]/;     // 원하는 특수문자 추가 제거

    if(val.length<8 || val.length>20){
        swal("영문+숫자+특수기호 8자리 이상으로 구성하여야 합니다.", '', 'warning');
        return false;
    }

    if(!pattern1.test(val)||!pattern2.test(val)||!pattern3.test(val)){
        swal("영문+숫자+특수기호 8자리 이상으로 구성하여야 합니다.", '', 'warning');
        return false;
    }
    return true;
}

var initScroll = function() {
    $('[data-scroll="true"]').each(function() {
        var el = $(this);
        KTUtil.scrollInit(this, {
            disableForMobile: true,
            handleWindowResize: true,
            height: function() {
                if (KTUtil.isInResponsiveRange('tablet-and-mobile') && el.data('mobile-height')) {
                    return el.data('mobile-height');
                } else {
                    return el.data('height');
                }
            }
        });
    });
}

function localToUTCDates(object, fields){
    var keys = Object.keys(object);
    _.each(keys, function(key){
        _.each(fields, function(field){
            if( key == field ){
                if( object[key] != null && object[key] != '' ){
                    object[key] = localToUTCDate(object[key]);
                }
            }
        })
    })
}

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}

window.froala_key = "ZOD3gB8C10B6A5E2B2C-8TMIBDIa1NTMNZFFPFZc1d1Ib2a1E1fA4A3G3I3F2C6D5C4E3G3==";