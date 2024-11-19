<!DOCTYPE html>
<html>
<head>
<style>
    /* Remove default margin and padding */
    html, body {
        margin: 0;
        padding: 0;
    }

    .navbar {
        display: flex;
        justify-content: center; /* Center the navigation buttons */
        align-items: center; /* Center items vertically */
        overflow: hidden;
        background-color: #6F4E37;
        position: fixed;
        top: 0;
        width: 100%;
        z-index: 1000; 
    }

    .navbar a {
        float: left;
        display: block;
        color: white;
        text-align: center;
        padding: 14px 16px;
        text-decoration: none;
        font-size: 20px;
    }

    .navbar a:hover {
        background: #ddd;
        color: black;
    }

    .row {
        display: flex;
        justify-content: center; 
        align-items: center; 
        gap: 20px; 
        margin-top: 80px; 
    }

    .column {
        width: 34%;
        text-align: 
    }

    img {
        width: 100%;
        height: auto;
    }

    input[type="text"] {
        width: 90%; 
        padding: 5px;
        margin-top: 10px; 
    }

    .centered-text {
        text-align: center;
        font-weight: bold;
    }
    
    .founders-section {
        display: flex;
        justify-content: center;
        gap: 20px;
        padding: 20px;
    }

    .founder {
        text-align: center;
        width: 18%; 
    }

    .founder img {
        width: 100%;
        border-radius: 8px;
    }

    .founder-name {
        margin-top: 8px;
        font-weight: bold;
        color: #333;
    }
</style>
</head>
<body>
    
<div class="navbar">
    <a href="http://18.222.229.220/wp-content/themes/myclass/home.php">Home</a>
    <a href="http://18.222.229.220/wp-content/themes/myclass/rsvp.php">Reserve Your Stay</a>
   <!-- <a href="http://18.222.229.220/wp-content/themes/myclass/rooms.html">Rooms</a>. not needed for this page-->
    <a href="http://18.222.229.220/wp-content/themes/myclass/login.php">Login</a>
    <a href="http://18.222.229.220/wp-content/themes/myclass/founders.php">Founders</a>
</div>

<!-- About the Founders Section -->
<div class="about-section">
    <h2>About the Founders</h2>
    <div class="founders-section">
        <!-- Founder 1 -->
       
<!---->
        <div class="founder">
            <img src="images/founder1.png" alt="FrontEnd">
            <div class="founder-name">Julie Luu</div>
        </div>

        <!-- Founder 2 -->
        <div class="founder">
            <img src="images/founder2.png" alt="FrontEnd">
            <div class="founder-name"> Sumeya Hussein</div>
        </div>
        <!-- Founder 3 -->
        <div class="founder">
            <img src="images/founder3.png" alt="Backend">
            <div class="founder-name">Alex Castro</div>
        </div>
        <!-- Founder 4 -->
        <div class="founder">
            <img src="images/founder4.png" alt="FrontEnd">
            <div class="founder-name">Muhammad Nadeem</div>
        </div>
        <!-- Founder 5 -->
        <div class="founder">
            <img src="images/founder5.png" alt="Backend">
            <div class="founder-name">Nathan Dermer</div>
        </div>
    </div>
</div>

<?php include 'footer.html'; ?> 
</body>
</html> 
