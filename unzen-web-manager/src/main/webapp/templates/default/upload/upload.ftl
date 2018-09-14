<div class="mask"></div>
<div id="uploader" class="wu-example">
	<!-- 用来存放文件信息 -->
	<div class="demo">
		<div id="asdsadad">
			<div class="queueList">
				<div id="filePicker" class="placeholder">
					<p class="sead">添加一张图片</p>
				</div>
			</div>
			<div id="filePicker2" class="goon" style="display:none;">
<!-- 					<span></span>继续添加 -->
			</div>
			
		</div>
	 	<div id="main" role="main">
			<textarea id="form-content" class="editor" cols="30" rows="10">
	        </textarea>
        </div>
        <div class="buttions">
           <div class="btns">
	           <div id="filePicker2"></div><div class="uploadBtn begin">开始上传</div>
	       </div>
           <div class="btns">
	           <div id="filePicker2"></div><div class=" cancel" onclick= "cancel()">取消</div>
	       </div>
        </div>
       
	</div>
	<input id="upload" type="hidden" value=""/>
</div>

<script type="text/javascript">
<!-- /***********************************富文本编辑区**********************************/ -->
	  $('#simple-editor').trumbowyg();
      $.trumbowyg.btnsGrps.test = ['bold', 'link'];
      
      <!-- Add new words for customs btnsDef just below -->
      $.extend(true, $.trumbowyg.langs, {
          fr: {
              align: 'Alignement',
              image: 'Image'
          }
      });

      $('#customized-buttonpane').trumbowyg({
          lang: 'fr',
          closable: true,
          fixedBtnPane: true,
          btnsDef: {
              // Customizables dropdowns
              align: {
                  dropdown: ['justifyLeft', 'justifyCenter', 'justifyRight', 'justifyFull'],
                  ico: 'justifyLeft'
              },
              image: {
                  dropdown: ['insertImage', 'upload', 'base64'],
                  ico: 'insertImage'
              }
          },
          btns: ['viewHTML',
              '|', 'formatting',
              '|', 'btnGrp-test',
              '|', 'align',
              '|', 'btnGrp-lists',
              '|', 'image']
      });

      <!-- Simple customization with current options -->
      $('#form-content').trumbowyg({
          lang: 'fr',
          closable: true,
          mobile: true,
          fixedBtnPane: true,
          fixedFullWidth: true,
          semantic: true,
          resetCss: true,
          autoAjustHeight: true,
          autogrow: true
      });

      $('.editor').on('dblclick', function(e){
          $(this).trumbowyg({
              lang: 'fr',
              closable: true,
              fixedBtnPane: true
          });
      });
      
      function cancel(){
      	$(".mask").hide();
      	$("#uploader").hide();
      	location.href=_base_path+"/unzen/";
      }
</script>
