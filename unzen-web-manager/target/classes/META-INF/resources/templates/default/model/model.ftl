<style>
	#clipArea {
		margin: 20px;
		height: 350px;
		width: 50%;
	}
	#view {
		margin: 0 auto;
		width: 200px;
		height: 200px;
		float:left;
	}
	.photo-clip-area{
		border: 1px solid rgb(221, 221, 221) !important;
	}
	.up-btn{
	    width: 40%;
	    margin: 0 auto;
	    position: relative;
	}
	.btn-choose{
		width: 100%;
	}
	#clipArea{
		float:left;
	}
	#view{
	    margin: 20px;
	}
	.body-btn{
	    width: 100%;
	    position: relative;
	    display: inline-block;
	}
	.save{
	    position: absolute;
	    bottom: 30px;
	    right: 250px;
	}
</style>
<!-- Modal -->
<div class="modal fade bs-example-modal-lg" data-backdrop='static' id="userMsg" tabindex="-1" role="dialog" aria-labelledby="userMsgLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">上传头像</h4>
      </div>
      <div class="modal-body">
      	<div class="up-btn">
	       	<button class="btn btn-group btn-success fileinput-button btn-choose" type="button">+点击选择</button>
	        <input type="file" id="file" onchange="loadFile(this.files[0])" style="position:absolute;top:0;left:0;font-size:34px; opacity:0;width: 100%;">
       	</div>
       	<div class="body-btn">
	       	<div id="clipArea"></div>
			<div id="view"></div>
			<button id="clipBtn" class="btn btn-primary save">截&nbsp;&nbsp;取</button>
       	</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary">保存</button>
      </div>
    </div>
  </div>
</div>

<script>
//document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
var clipArea = new bjj.PhotoClip("#clipArea", {
	size: [260, 260],
	outputSize: [640, 640],
	file: "#file",
	view: "#view",
	ok: "#clipBtn",
	loadStart: function() {
		console.log("照片读取中");
	},
	loadComplete: function() {
		console.log("照片读取完成");
	},
	clipFinish: function(dataURL) {
		console.log(dataURL);
	}
});
//clipArea.destroy();
</script>
