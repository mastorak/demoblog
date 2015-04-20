/**
 * @author mastorak
 */

$(function(){
	
	$('#postdiv').hide();
	$('#editordiv').hide();
	$('#editpostBtn').hide();
	$('#savepostBtn').hide();
	$('#deletepostBtn').hide();
	$('#backBtn').hide();

	var document;
	
	//load all posts on page load
	loadAllPosts();

	/**
	 * Function that calls the getallposts REST service iterates through the results
	 * and creates an entry for each
	 */
	function loadAllPosts(){
		
		$("#postlistdiv").empty();
		
		//call the getallposts REST service
		$.ajax({
			type : 'GET',
			url : 'getallposts',
			dataType : 'json',
			success : function(data) {
				//check if we got data back
				if(data.length>0){
					$.each(data,function( index, item ) {
						$("#postlistdiv").append(createPostDiv(item));
					});
				}
				else{
					console.log(data)
					$("#postlistdiv").append("No posts found");
				}
			}
		});
	}
	
	/**
	 * Function that creates an html div element for each post 
	 */
	function createPostDiv(item) {

			var postDiv = jQuery('<div style="margin:20px"/>', {
				id : item.id,
			});
			var postTitle =jQuery('<h3 class="bg-primary">'+item.title+'</h3>');
			
			 postTitle.hover(function() {
			        $(this).css('cursor','pointer');
			    });
			 
			postTitle.on( "click", function() {
				$('#postlistdiv').hide();
				showPost(item.id);
			});
			
			postDiv.append(postTitle);
			postDiv.append('<p class="text-primary">'+item.text+'</p>');
			postDiv.append('<small class="bg-warning"> by '+item.userId+' on '+new Date(item.created)+'</small>');
			
			return postDiv;
		}
	
	/**
	 * Function calls the getpost REST service and creates the entry for that post
	 * @parm postId the post id 
	 */
	function showPost(postId){
		
		//call the getpost REST service
		$.ajax({
			type : 'GET',
			url : 'getpost',
			data : {
				"id" : postId //we pass the id as a json string
			},
			dataType : 'json',
			success : function(data) {
				var lastUpdated=data.updated;
				if(lastUpdated==null){
					lastUpdated=data.created;
				}
				//create the html representation of the post 
				$('#postdiv').html('<div style="margin:20px"><h3 class="bg-primary">'+data.title+'</h3><p class="text-primary">'+data.text+'</p><small class="bg-warning">last updated: '+new Date(lastUpdated)+'</small></div>');
				$('#postdiv').show();
				$('#editordiv').hide();
				
				$('#newpostBtn').hide();
				$('#savepostBtn').hide();
				$('#editpostBtn').show();
				$('#deletepostBtn').show();
				$('#backBtn').show();
				
				document=data;
			}
		});

	}
	
	/**
	 * Function shows the post editor and fills in data if applicable 
	 * @param post the Post object. If it exist we fill in the form elements.
	 */
	function showEditor(post){
		
		$('#newpostBtn').hide();
		$('#editpostBtn').hide();
		$('#savepostBtn').show();
		$('#deletepostBtn').hide();
		$('#backBtn').show();
		
		$('#editordiv').show();
		$('#postdiv').hide();
		$('#postlistdiv').hide();
		
		//if we edit an existing post we fill in the form
		if(post!=null){
			$('#edittitle').val(post.title);
			$('#edittext').val(post.text);
			$('#postid').val(post.id);
		//if we create a new post we empty the fields	
		}else{
			$('#edittitle').val('');
			$('#edittext').val('');
			$('#postid').val('');
		}
		
	}
	
	/**
	 * Function that calls the deletepost REST service.
	 * @param postId the id of the post we want to delete
	 */
	function deletePost(postId){
		
		var dataObj = {};
		dataObj["id"] = postId;
		
		//call the deletepost REST service
		$.ajax({
			type : 'DELETE',
			url : 'deletepost',
			data : JSON.stringify(dataObj),
			dataType : 'json',
			contentType : 'application/json',
			mimeType : 'application/json',
			success : function(data) {
				$('#postdiv').hide();
				$('#editordiv').hide();
				$('#postlistdiv').show();

				$('#backBtn').hide();
				$('#savepostBtn').hide();
				$('#editpostBtn').hide();
				$('#deletepostBtn').hide();
				$('#newpostBtn').show();
				// when going back load again the updated list
				loadAllPosts();
			},
			error : function(data, status, er) {
				alert(status + "," + er);
			}
		});
		
	}
	
	/**
	 * Function that takes the values from the editor form creates a Post object
	 * and calls the saveorupdate REST service to update the post.  
	 */
	function saveOrUpdatePost(){
		
		//get the values from the input elements
		var title=$('#edittitle').val();
		var text=$('#edittext').val();
		var id=$('#postid').val();
		
		//create the data object and fill it in
		var dataObj = {};
		dataObj["id"] = id;
		dataObj["title"] = title;
		dataObj["text"] = text;
		
		//make the call to saveorupdate REST Service
		$.ajax({ 
		    url: "saveorupdatepost", 
		    type: 'POST', 
		    dataType: 'json', 
		    data: JSON.stringify(dataObj), //convert to JSON String to pass to the service
		    contentType: 'application/json',
		    mimeType: 'application/json',
		    success: function(data) { 
		    	showPost(data.id);
		    },
		    error:function(data,status,er) { 
		        alert(status+","+er);
		    }
		});
		
	}
	

	/**       Button Handlers          **/
	
	$('#newpostBtn').on('click', function(){
		showEditor(null);
	});
	
	$('#editpostBtn').on('click', function(){
		showEditor(document);
	});
	
	$('#backBtn').on('click',function(){
		$('#postdiv').hide();
		$('#editordiv').hide();
		$('#postlistdiv').show();
		
		$('#backBtn').hide();
		$('#savepostBtn').hide();
		$('#editpostBtn').hide();
		$('#newpostBtn').show();
		$('#deletepostBtn').hide();
		//when going back load again the updated list
		loadAllPosts();
	});
	
	
	$('#savepostBtn').on('click', function(){
		saveOrUpdatePost();
	});
	
	$('#deletepostBtn').on('click', function(){
		deletePost(document.id);
	});

});