<?php

/**
 * usage: 请先下载phpMail类。
 *
 * Supports
 * 1.支持给多人发邮件
 * 2.支持html
 * 3.只需要简单配置即可使用。
 * 
 * Tips
 * 1.编码：
 * $mail->CharSet = "utf-8";
 * $mail->Subject = "=?utf-8?B?" . base64_encode($mailSubject) . "?=";
 *
 * Todos
 * 1.发附件
 */

/**
 * 邮件发送
 * @param type $mailConfig
 * @param type $mailTo
 * @param type $mailSubject
 * @param type $mailContent
 */
function sendMail($mailConfigArray, $mailToArray, $mailSubject, $mailContent)
{
    // 配置
//      $mailConfigArray = array(
//          "host" => "smtp.8888.com.cn",
//          "port" => 25,
//          "user_name" => "8888@8888.com.cn",
//          "password" => "8888" ,
//          "from" => "8888@8888.com.cn",
//          "from_name" => "8888@8888.com.cn",
//      );
    // 邮件人
//      $mailToArray = array(
//          "8888@8888.com.cn" , "8888@8888.com.cn"
//      );
    require_once COMMON_PATH .'Ext/email/class.phpmailer.php';

    $mail = new PHPMailer();
    $mail->IsSMTP();
    $mail->IsHTML(true);
    $mail->Host = "{$mailConfigArray['host']}";
    $mail->Port = $mailConfigArray['port'];
    $mail->SMTPAuth = true;
    $mail->Username = "{$mailConfigArray['user_name']}";
    $mail->Password = "{$mailConfigArray['password']}";
    $mail->From = isset($mailConfigArray['from']) ? "{$mailConfigArray['from']}" : "{$mailConfigArray['user_name']}";
    $mail->FromName = isset($mailConfigArray['from_name']) ? "{$mailConfigArray['from_name']}" : "{$mailConfigArray['user_name']}";

    if (!empty($mailToArray)) {
        if (is_array($mailToArray)) {
            foreach ($mailToArray as $mailTo) {
                $mail->AddAddress($mailTo);
            }
        } else {
            $mail->AddAddress($mailToArray);
        }
    }

    $mail->CharSet = "utf-8";
    // $mail->CharSet = "GBK";
    $mail->Subject = "=?utf-8?B?" . base64_encode($mailSubject) . "?=";
    $mail->Body = $mailContent;

    //print_r($mail);exit();
    if (!$mail->Send()) {

        echo "Message could not be sent. <p>";
        echo "Mailer Error: " . $mail->ErrorInfo;
        return false;
    } else {
        //echo "ok";
        return true;
    }
    //return !$mail->Send() ? false : true;
}
