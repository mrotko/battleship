<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

    require 'vendor/autoload.php';
    require_once("Register.class.php");
    require_once("Login.class.php");
    require_once("ResetPassword.class.php");
    require_once("User.class.php");
    require_once("Connection.class.php");
    //
    // $app = new \Slim\App;
    // $app->get('/hello/{name}', function (Request $request, Response $response) {
    //     $name = $request->getAttribute('name');
    //     $response->getBody()->write("Hello, $name");
    //
    //     return $response;
    // });
    // $app->run();

    $connection = new Connection();

    $response = array("error" => false);
    if($_POST['request']) {
        $request = json_decode($_POST['request']);


        var_dump($request);

        switch($request['type']) {
            case 'LOGIN':
                $login = new Login($connection);
                break;
            case 'REGISTER':
                $register = new Register($connection);
                $user = new User($request['user']);
                $result = $register->register($user);
                $response['error'] = !$result;
                break;
            case 'RESET_PASSWORD':
                break;
        }
    }

    echo json_encode($response, JSON_UNESCAPED_UNICODE);
?>
