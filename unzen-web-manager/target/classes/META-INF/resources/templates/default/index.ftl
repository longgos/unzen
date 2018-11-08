<#include "/default/utils/ui.ftl"/>

<#-- aaaaaaaaaaaaaa -->
<@layout>
<div class="row streams">
    <div class="col-xs-12 col-md-6 side-left">
        <div class="panel-default">
            <@contents pn=pn order=order>

                <div class="panel-body remove-padding-horizontal">

                    <ul class="list-group row topic-list">
                        <#list results.datas as row>
                            <li class="li-group-coln">
                               <div class="list-group-item user-pair">
			                      <!-- <a class="reply_count_area hidden-xs pull-right" href="#">
			                          <div class="count_set">
			                              <span class="count_of_votes" data-toggle="tooltip" title="阅读数">${row.views}</span>
		                               	  <span class="count_seperator">/</span>
			                              <span class="count_of_replies" data-toggle="tooltip" title="回复数">${row.comments}</span>
			                              <span class="count_seperator">/</span>
			                              <span class="count_of_visits" data-toggle="tooltip" title="点赞数">${row.favors}</span>
			                              <span class="count_seperator">|</span>
			                              <abbr class="timeago">${timeAgo(row.created)}</abbr>
			                          </div>
			                      </a> -->
			                      <div class="avatar pull-left">
			                          <a href="${base}/users/${row.author.id}">
			                              <img class="media-object img-thumbnail avatar avatar-middle"
			                                   src="${base}/${row.author.avatar}"/>
			                          </a>
			                      </div>
			                      <div class="ms-name"><a href="${base}/users/${row.author.id}">${row.author.name}</a></div>
                               </div>
                               <div class="user-msg">
                               		<#list row.picturesPath?split(",") as path>
                               			<img style="width:100%;" src="${base}/image/${path}"/>
                               		</#list>
                               </div>
                               <div class="list-group-item blog-msg">
                               <!-- href="${base}/post/like?postId=${row.id}" -->
	                               <a onclick="hots(this,${row.id})" class="btn i-icon">
			                    		<i class="btn-large icon icon-heart hot-heart" title="热度"></i>
			                       </a>
	                               <a href="${base}/post/new" class="btn i-icon">
			                    		<i class="btn-large icon icon-bubble hot-heart say" title="评论"></i>
			                       </a>
			                       <abbr class="timeago">${timeAgo(row.created)}</abbr>
                               </div>
                               <div class="list-group-item">
                           		   <span id="hot_${row.id}" class="hot">${row.favors} 热度</span>
                           		   <div class="item-title">
                           		   	<@classify row/>
                           		   	<!--<a class="item_user" href="${base}/users/${row.author.id}">${row.author.name}</a>
                           		   	：-->
                           		   	<a href="${base}/view/${row.id}">${row.summary}</a>
                           		   </div>
                           	   </div>
                           	   
                           	   <div class="usecard"></div>
                            </li>
                        </#list>

                        <#if  results.datas?size == 0>
                            <li class="list-group-item ">
                                <div class="infos">
                                    <div class="media-heading">该目录下还没有内容!</div>
                                </div>
                            </li>
                        </#if>
                    </ul>
                </div>
                <div class="panel-footer text-right remove-padding-horizontal pager-footer">
                    <!-- Pager -->
                    <@pager request.requestURI!"", results, 5/>
                </div>
            </@contents>
        </div>
    </div>
    <div class="col-xs-12 col-md-4 hidden-xs side-right">
        <#include "/default/inc/right.ftl" />
    </div>
</div>
<script type="text/javascript">
	function hots(obj,postId){
		console.log("点赞请求开始...")
		$.ajax({
			type:'post',
			url:'${base}/post/like?postId='+postId,
			success:function(result){
				<!--console.log(result+"热度!")-->
				$('#hot_'+postId).html(result+"热度");
			}
		})
	}
</script>

</@layout>
