var tableTemplate;  
 
var currentNode; // The currently selected node.
var stack = new Array(); // Stack of previously selected nodes.

/**********
 ** Init **
 **********/

$(document).ready(init);
	
function init () {
	currentNode = $.parseJSON(data);
	createTableTemplate();
	fillTables();
}

function createTableTemplate () {
	tableTemplate = $('#currentNode').html();
}

function fillTables() {
	fillParentNode();
	fillCurrentNode();
	fillChildren();
}

function fillParentNode() {
	if (stack.length>0) {
		fillNode(stack[stack.length-1],"previousNode");
	}
	else {
		fillNode({type:"",currentGoal:"",nextGoals:[],assignments:{},abducibles:[],denials:[],equalities:[],constraints:[],mark:"",children:[],nestedDenials:[]},"previousNode");
	}
}

function fillCurrentNode() {
	fillNode(currentNode,"currentNode")
}

function fillChildren() {
	var newTable, div;
	
	var heading = document.createElement('h2');
	$(heading).text("Children")
	$('#children').html(heading);
	
	for (child in currentNode.children) {		
		var newDiv = document.createElement('div');
		
		$(newDiv).attr("id",child);
		$(newDiv).attr("class","child-div");
		
		var newTable = document.createElement('table');
		$(newTable).attr("id","child-table"+child);
		$(newTable).attr("class","childNodeTable");
		
		$(newTable).html(tableTemplate);
		
		var title = document.createElement('h3');
		$(title).text("Child "+child);
		
		$(newDiv).append(title);
		$(newDiv).append(newTable);
		
		var chooseButton = document.createElement('input');
		$(chooseButton).attr("type","button");
		$(chooseButton).attr("class","choose-button");
		$(chooseButton).attr("value","Choose");
		$(chooseButton).attr("onClick","choose($(this).parent())");
		$(newDiv).append(chooseButton);
		
		$('#children').append(newDiv);
		fillNode(currentNode.children[child],"child-table"+child);
		
	}
	
}

/************
 ** Tables **
 ************/

function fillNode(node,tableId) {
	fillType(node.type,tableId);
	fillCurrentGoal(node.currentGoal,tableId);
	fillNestedDenials(node.nestedDenials,tableId);
	fillNextGoals(node.nextGoals,tableId);
	fillAssignments(node.assignments,tableId);
	fillAbducibles(node.abducibles,tableId);
	fillDenials(node.denials,tableId);
	fillEqualities(node.equalities,tableId);
	fillConstraints(node.constraints,tableId);
	fillNodeMark(node.mark,tableId);
}

function fillType(type,tableId) {
	$("#"+tableId+" #type").text(type);
}

function fillCurrentGoal(type,tableId) {
	$("#"+tableId+" #current-goal").text(type);
}

function fillNestedDenials(denialArray, tableId) {
	var ul,li;

	ul = document.createElement('ul');
	for (var denial in denialArray) {
		li = document.createElement('li');
		$(li).text(denialArray[denial]);
		ul.appendChild(li);
	}
	$("#"+tableId+" #nested-denials").html(ul);
}

function fillNextGoals(goalArray, tableId) {
	var ul,li;

	ul = document.createElement('ul');
	for (var goal in goalArray) {
		li = document.createElement('li');
		$(li).text(goalArray[goal]);
		ul.appendChild(li);
	}
	$("#"+tableId+" #next-goals").html(ul);
}

function fillAssignments(assignments, tableId) {
	var ul,li;

	ul = document.createElement('ul');
	for (var assignment in assignments) {
		li = document.createElement('li');
		$(li).text(assignment+"/"+assignments[assignment]);
		ul.appendChild(li);
	}
	$("#"+tableId+" #assignments").html(ul);
}

function fillAbducibles(abducibleArray, tableId) {
	var ul,li;

	ul = document.createElement('ul');
	for (var abducible in abducibleArray) {
		li = document.createElement('li');
		$(li).text(abducibleArray[abducible]);
		ul.appendChild(li);
	}
	$("#"+tableId+" #abducibles").html(ul);
}

function fillDenials(denialArray, tableId) {
	var ul,li;

	ul = document.createElement('ul');
	for (var denial in denialArray) {
		li = document.createElement('li');
		$(li).text(denialArray[denial]);
		ul.appendChild(li);
	}
	$("#"+tableId+" #denials").html(ul);
}

function fillEqualities(equalityArray, tableId) {
	var ul,li;

	ul = document.createElement('ul');
	for (var equality in equalityArray) {
		li = document.createElement('li');
		$(li).text(equalityArray[equality]);
		ul.appendChild(li);
	}
	$("#"+tableId+" #equalities").html(ul);
}

function fillConstraints(constraintArray, tableId) {
	var ul,li;

	ul = document.createElement('ul');
	for (var constraint in constraintArray) {
		li = document.createElement('li');
		$(li).text(constraintArray[constraint]);
		ul.appendChild(li);
	}
	$("#"+tableId+" #constraints").html(ul);
}

function fillNodeMark(nodeMark, tableId) {
	$("#"+tableId+" #mark").text(nodeMark);
}

/*****************
 ** Interaction **
 *****************/

function choose(childDiv) {
	stack.push(currentNode);
	currentNode = currentNode.children[$(childDiv).attr("id")]
	fillTables()
}

function back() {
	if (stack.length>0) {
		currentNode=stack.pop();
		fillTables();
	}
	else {
		alert("Current node has no parent.");
	}
}
