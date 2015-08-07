<html>
  <head>
    <script type='text/javascript' src='../js/jquery-2.1.4.js'></script>
    <script type='text/javascript'>
$(document).ready( function() {
    $('#tmpOpen').click(function() {
        $('#tmpDialogue').slideDown(100);
      }
    );
    
    $('#tmpClose').click( function($e) {      
        $('#tmpDialogue').slideUp(100);
      }
    );
    
  }
);
    </script>
    <style type='text/css'>
#tmpDialogue {
    display: none;
    position: absolute;
    top: 50%;
    left: 50%;
    width: 500px;
    height: 200px;
    margin: -100px 0 0 -250px;    
    background: maroon;
    border: 5px solid rgb(128, 128, 128);
}
#tmpButtons {
    position: absolute;
    bottom: 5px;
    right: 5px;
}
div#tmpDialogue p {
color: green;
}

    </style>
    
  </head>
  <body>
     <input type="button" id='tmpOpen' value='Open' />
     <div id='tmpDialogue'>
       <p>
         Dialog content / Testing
       </p>
       <div id='tmpButtons'>
         <input type="button" id='tmpClose' value='Close' />
       </div>
     </div>
  </body>
</html>