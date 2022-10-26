<?php
// Set the location for simplepie cache
function generarFeeds(){
require_once 'libs/SimplePie/autoloader.php';

$url = 'http://feeds.weblogssl.com/xataka2';
$feed = new SimplePie();
$feed->set_feed_url($url);
$feed->enable_cache();
$feed->set_cache_location('C:\xampp\htdocs\libs\simplepie-1.5/cache');
$_SERVER['DOCUMENT_ROOT'] . '/app/cache_files"';
$feed->init();

//echo '<h1>' . $feed->get_title() . '</h1>';
//echo '<p>' . $feed->get_description() . '</p>';

foreach($feed->get_items() as $items):
    $feeds .= '<article>';
    $feeds .= '<header>';
    $feeds .= '<p>Title: <a href="' . $item->get_link() . '".' .
    $item->get_tittle() . '</a></p>';
    $feeds .= '<p>Author: <a href="' . $item->get_author()->get_name() . '".' .
    '</p>'
    $feeds .='<p>Date: ' . $item->get_date('Y-m-d H:i:s') . '</p>';
    $feeds .='<p>Description: ' . $item->get_description() . '</p>';
    $feeds .='</header>';
    $feeds .=$item->get_content(true);
    $feeds .='</article>';
endforeach
return &feeds;
/*
$itemQty = $feed->get_item_quantity();
for ($i = 0; $i < $itemQty; $i++) {
    $item = $feed->get_item($i);
    echo '<article>';
    echo '<header>';
    echo '<p>Title: <a href="' . $item->get_link() . '">' . $item->get_title() . '</a></p>';
    echo '<p>Author: ' . $item->get_author()->get_name() . '</p>';
    echo '<p>Date: ' . $item->get_date('Y-m-d H:i:s') . '</p>';
    echo '<p>Description: ' . $item->get_description() . '</p>';
    echo '</header>';
    echo $item->get_content(true);
    echo '</article>';
}*/
/*
$feed = new SimplePie();
$feed->set_feed_url($url);
$feed->enable_cache();
$feed->init();

$feed = new SimplePie();
$feed->set_feed_url($url);
$feed->enable_cache();
$feed->init();
*/}