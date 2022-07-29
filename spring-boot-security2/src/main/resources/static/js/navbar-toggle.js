/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

/*sidebar toggle*/
$('.side-nav .btn-toggle').each(function () {
    $(this).on('click', function () {
        $('.side-nav .btn-toggle').not(this).each(function () {
            if ($(this).parent().next('ul').css('display') === 'block') {
                $(this).parent().next('ul').slideToggle(200);
            }
        });
        $(this).parent().next('ul').slideToggle(200);
    });
});
