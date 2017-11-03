/**
 * 
 */

function confirmDelete(deleteUrl) {
	if (confirm('Delete record?')) {
		window.location.href = deleteUrl;
	}
	
	else {
		
	}
}