jQuery(document).ready(function ($) {

    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 100) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({ scrollTop: 0 }, 1500, 'easeInOutExpo');
        return false;
    });

    // Initiate superfish on nav menu
    $('.nav-menu').superfish({
        animation: {
            opacity: 'show'
        },
        speed: 200
    });

    // Mobile Navigation
    if ($('#nav-menu-container').length) {
        var $mobile_nav = $('#nav-menu-container').clone().prop({
            id: 'mobile-nav'
        });
        $mobile_nav.find('> ul').attr({
            'class': '',
            'id': ''
        });
        $('#header').append($mobile_nav);
        $('#header .tp-header').prepend('<button type="button" id="mobile-nav-toggle"><i class="las la-bars"></i></button>');
        $('#header').append('<div id="mobile-body-overly"></div>');
        $('#mobile-nav').find('.menu-has-children').prepend('<i class="las la-angle-down"></i>');

        $(document).on('click', '.menu-has-children i', function (e) {
            $(this).next().toggleClass('menu-item-active');
            $(this).nextAll('ul').eq(0).slideToggle();
            $(this).toggleClass("la-angle-up la-angle-down");
        });

        $(document).on('click', '#mobile-nav-toggle', function (e) {
            $('body').toggleClass('mobile-nav-active');
            $('#mobile-nav-toggle i').toggleClass('la-times la-bars');
            $('#mobile-body-overly').toggle();
        });

        $(document).click(function (e) {
            var container = $("#mobile-nav, #mobile-nav-toggle");
            if (!container.is(e.target) && container.has(e.target).length === 0) {
                if ($('body').hasClass('mobile-nav-active')) {
                    $('body').removeClass('mobile-nav-active');
                    $('#mobile-nav-toggle i').toggleClass('la-times la-bars');
                    $('#mobile-body-overly').fadeOut();
                }
            }
        });
    } else if ($("#mobile-nav, #mobile-nav-toggle").length) {
        $("#mobile-nav, #mobile-nav-toggle").hide();
    }

    // Smooth scroll for the menu and links with .scrollto classes
    $('.nav-menu a, #mobile-nav a, .scrollto').on('click', function () {
        if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
            var target = $(this.hash);
            if (target.length) {
                var top_space = 0;

                if ($('#header').length) {
                    top_space = $('#header').outerHeight();

                    if (!$('#header').hasClass('header-fixed')) {
                        top_space = top_space - 20;
                    }
                }

                $('html, body').animate({
                    scrollTop: target.offset().top - top_space
                }, 1500, 'easeInOutExpo');

                if ($(this).parents('.nav-menu').length) {
                    $('.nav-menu .menu-active').removeClass('menu-active');
                    $(this).closest('li').addClass('menu-active');
                }

                if ($('body').hasClass('mobile-nav-active')) {
                    $('body').removeClass('mobile-nav-active');
                    $('#mobile-nav-toggle i').toggleClass('fa-times fa-bars');
                    $('#mobile-body-overly').fadeOut();
                }
                return false;
            }
        }
    });
    // logout
    $('#logout').click(function(){
    	deleteAllCookies();
    })
    // Header scroll class
    $(window).scroll(function () {
        if ($(this).scrollTop() > 150) {
            $('#header').addClass('header-scrolled');
        } else {
            $('#header').removeClass('header-scrolled');
        }
    });

    // minimum setup
    $('.selectpicker').selectpicker({
        size: '6',
        liveSearch: true,
        container: "body"
    });
    $('.selectpickernone').selectpicker({
        size: '6',
        container: "body"
    });

    // Date time
    $('.dateranger-one .date-use').prop('readonly', true);
    $('.dateranger-one').daterangepicker({
        opens: 'right',
        autoApply: true,
        singleDatePicker: true
    }, function (start, end, label) {
        $('.dateranger-one .date-use').val(start.format('DD-MM-YYYY'));
    });


    // Mobile Search
    var searchBox = document.querySelectorAll('.search-box input[type="text"] + span');

    searchBox.forEach((elm) => {
        elm.addEventListener('click', () => {
            elm.previousElementSibling.value = '';
        });
    });

    // Pro carousel Home
    $('.owl-carousel').owlCarousel({
        loop: true,
        margin: 30,
        navText: ['<i class="las la-arrow-left"></i>', '<i class="las la-arrow-right"></i>'],
        nav: true,
        dots: false,

        responsive: {
            0: {
                items: 1,
                nav: true
            },
            576: {
                items: 1,
                nav: true
            },
            768: {
                items: 2,
                nav: true
            },
            1120: {
                items: 4,
                nav: true
            }
        }
    })

    $('#gohis').click(function () {
        $('html, body').animate({
            scrollTop: $("#lich-su-dau-gia").offset().top - 120
        }, 2000);
        return false;
    });

    $('#goreg').click(function () {
        $('html, body').animate({
            scrollTop: $("#danh-sach-dang-ky").offset().top - 120
        }, 2000);
        return false;
    });

    // Preloading
    $("#loading").delay(300).fadeOut(500);

});
function setCookie(cname, cvalue, exdays) {
	  let d = new Date();
	  d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
	  let expires = "expires="+d.toUTCString();
	  document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
	}
function deleteAllCookies() {
    var cookies = document.cookie.split(";");

    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i];
        var eqPos = cookie.indexOf("=");
        var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
    }
}
	function getCookie(cname) {
	  let name = cname + "=";
	  let ca = document.cookie.split(';');
	  for(var i = 0; i < ca.length; i++) {
	    var c = ca[i];
	    while (c.charAt(0) == ' ') {
	      c = c.substring(1);
	    }
	    if (c.indexOf(name) == 0) {
	      return c.substring(name.length, c.length);
	    }
	  }
	  return "";
	}

