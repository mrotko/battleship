<?php
    require_once("Register.class.php");
    require_once("Login.class.php");
    require_once("ResetPassword.class.php");
    require_once("User.class.php");
    require_once("Connection.class.php");

    $connection = new Connection();

    $response = array("status" => 2);

    if(isset($_POST['type'])) {
        switch($_POST['type']) {
            case 'login':
                $login = new Login($connection);
                $user = $login->tryLoginByEmail($_POST['email'], $_POST['hashPass']);
                if($user != NULL) {
                    $response['status'] = 0;
                    $response['userData'] = $user->getArray();
                } else {
                    $response['status'] = 1;
                }
                break;
            case 'register':
                $register = new Register($connection);
                $user = new User(array( 'email' => $_POST['email'],
                                        'customName' => $_POST['customName'],
                                        'googleId' => $_POST['googleId'],
                                        'hashPass' => $_POST['hashPass'],
                                        'points' => $_POST['points'],
                                        'level' => $_POST['level']
                                    ));
                $result = $register->register($user);
                if($result) {
                    $response['status'] = 0;
                } else {
                    $response['status'] = 1;
                }
                break;
            case 'RESET_PASSWORD':
                break;
        }
    }

    echo json_encode($response, JSON_UNESCAPED_UNICODE);
?>
