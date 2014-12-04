// make console.log safe to use
window.console || (console = {log: function() {}});

//------------- Options for Super Mario -------------//
var smOptions = {
    fixedWidth: false//activate fixed version with true
};


// document ready function
$(document).ready(function() {
    $.egox.ui.tabs.init();

    //window resize events
    $(window).resize(function() {
        //get the window size

        var wSize = $(window).width();
        if (wSize > 980) {
            $('.shortcuts.hided').removeClass('hided').attr("style", "");
            $('.sidenav.hided').removeClass('hided').attr("style", "");
        }
        $.egox.ui.tabs.resize();
//    var size = "Window size is:" + $(window).width();
        //console.log(size);
    });

    $(window).load(function() {
        var wHeight = $(window).height();
        $('#sidebar.scrolled').css('height', wHeight - 63 + 'px');
    });


    //make template fixed width
    if (smOptions.fixedWidth) {
        $('body').addClass('fixedWidth');
        $('#header').addClass('container');
        $('#wrapper').addClass('container');
    }

    //localStorage.setItem('rtl', 0);

    //Disable certain links
    $('a[href^=#]').click(function(e) {
        e.preventDefault();
    });

    //------------- Navigation -------------//

    var mainNav = $('.mainnav>ul>li');
    mainNav.find('ul').siblings().addClass('hasUl').append('<span class="hasDrop icon16 icomoon-icon-arrow-down-2"></span>');
    var mainNavLink = mainNav.find('a').not('.sub a');
    var mainNavLinkAll = mainNav.find('a');
    var mainNavSubLink = mainNav.find('.sub a').not('.sub li');
    var mainNavCurrent = mainNav.find('a.current');

    //add hasSub to first element
    if (mainNavLink.hasClass('hasUl')) {
        $(this).closest('li').addClass('hasSub');
    }

    /*Auto current system in main navigation */
    var domain = document.domain;
    var folder = '';//if you put site in folder not in main domain you need to specify it. example http://www.host.com/folder/site
    var absoluteUrl = 0; //put value of 1 if use absolute path links. example http://www.host.com/dashboard instead of /dashboard

    function setCurrentClass(mainNavLinkAll, url) {
        mainNavLinkAll.each(function(index) {
            //convert href to array and get last element
            var href = $(this).attr('href');

            if (href == url) {
                //set new current class
                $(this).addClass('current');

                parents = $(this).parentsUntil('li.hasSub');
                parents.each(function() {
                    if ($(this).hasClass('sub')) {
                        //its a part of sub menu need to expand this menu
                        $(this).prev('a.hasUl').addClass('drop');
                        $(this).addClass('expand');
                    }
                });
            }
        });
    }


    if (domain === '') {
        //domain not found looks like is in testing phase
        var pageUrl = window.location.pathname.split('/');
        var winLoc = pageUrl.pop(); // get last item
        setCurrentClass(mainNavLinkAll, winLoc);

    } else {
        if (absoluteUrl === 0) {
            //absolute url is disabled
            var afterDomain = window.location.pathname;
            if (folder != '') {
                afterDomain = afterDomain.replace(folder + '/', '');
            } else {
                afterDomain = afterDomain.replace('/', '');
            }
            setCurrentClass(mainNavLinkAll, afterDomain);
        } else {
            //absolute url is enabled
            var newDomain = 'http://' + domain + window.location.pathname;
            setCurrentClass(mainNavLinkAll, newDomain);
        }
    }

    //hover magic add blue color to icons when hover - remove or change the class if not you like.
    mainNavLinkAll.hover(
            function() {
                $(this).find('span.icon16').addClass('blue');
            },
            function() {
                $(this).find('span.icon16').removeClass('blue');
            }
    );

    //click magic
    mainNavLink.click(function(event) {
        var $this = $(this);
        if ($this.hasClass('hasUl')) {
            event.preventDefault();
            if ($this.hasClass('drop')) {
                $(this).siblings('ul.sub').slideUp(250).siblings().toggleClass('drop');
            } else {
                $(this).siblings('ul.sub').slideDown(250).siblings().toggleClass('drop');
            }
        }
    });
    mainNavSubLink.click(function(event) {
        var $this = $(this);
        if ($this.hasClass('hasUl')) {
            event.preventDefault();
            if ($this.hasClass('drop')) {
                $(this).siblings('ul.sub').slideUp(250).siblings().toggleClass('drop');
            } else {
                $(this).siblings('ul.sub').slideDown(250).siblings().toggleClass('drop');
            }
        } else {
            event.preventDefault();
            var href = $this.attr('href');
            var title = $this.text();
            $.egox.ui.tabs.openTab(title, href);
        }
    });

    //responsive buttons
    $('.resBtn>a').click(function(event) {
        var $this = $(this);
        if ($this.hasClass('drop')) {
            $this.removeClass('drop');
        } else {
            $this.addClass('drop');
        }
        if ($('#sidebar').length) {
            $('#sidebar').toggleClass('offCanvas');
            $('#sidebarbg').toggleClass('offCanvas');
            if ($('#sidebar-right').length) {
                $('#sidebar-right').toggleClass('offCanvas');
            }
        }
        if ($('#sidebar-right').length) {
            $('#sidebar-right').toggleClass('offCanvas');
            $('#sidebarbg-right').toggleClass('offCanvas');
        }
        $('#content').toggleClass('offCanvas');
        if ($('#content-one').length) {
            $('#content-one').toggleClass('offCanvas');
        }
    });

    //Hide and show sidebar btn
    $(function() {
        //var pages = ['http://www.suggeelson.com/themes/supr/js/grid.html','http://www.suggeelson.com/themes/supr/js/charts.html'];
        var pages = [];

        for (var i = 0, j = pages.length; i < j; i++) {

            if ($.cookie("currentPage") == pages[i]) {
                var cBtn = $('.collapseBtn.leftbar');
                cBtn.children('a').attr('title', 'Show Left Sidebar');
                cBtn.addClass('shadow hide');
                cBtn.css({'top': '20px', 'left': '200px'});
                $('#sidebarbg').css('margin-left', '-299' + 'px');
                $('#sidebar').css('margin-left', '-299' + 'px');
                if ($('#content').length) {
                    $('#content').css('margin-left', '0');
                }
                if ($('#content-two').length) {
                    $('#content-two').css('margin-left', '0');
                }
                $.egox.ui.tabs.resize();
            }

        }

    });

    $('.collapseBtn').bind('click', function() {
        var $this = $(this);

        //left sidbar clicked
        if ($this.hasClass('leftbar')) {
            if ($(this).hasClass('hide-sidebar')) {
                //show sidebar
                $this.removeClass('hide-sidebar');
                $this.children('a').attr('title', 'Hide Left Sidebar');

            } else {
                //hide sidebar
                $this.addClass('hide-sidebar');
                $this.children('a').attr('title', 'Show Left Sidebar');
            }
            $('#sidebarbg').toggleClass('hided');
            $('#sidebar').toggleClass('hided');
            $('.collapseBtn.leftbar').toggleClass('top shadow');
            //expand content

            if ($('#content').length) {
                $('#content').toggleClass('hided');
            }
            if ($('#content-two').length) {
                $('#content-two').toggleClass('hided');
            }
        }

        //right sidebar clicked
        if ($this.hasClass('rightbar')) {
            if ($(this).hasClass('hide-sidebar')) {
                //show sidebar
                $this.removeClass('hide-sidebar');
                $this.children('a').attr('title', 'Hide Right Sidebar');

            } else {
                //hide sidebar
                $this.addClass('hide-sidebar');
                $this.children('a').attr('title', 'Show Right Sidebar');
            }
            $('#sidebarbg-right').toggleClass('hided');
            $('#sidebar-right').toggleClass('hided');
            if ($('#content').length) {
                $('#content').toggleClass('hided-right');
            }
            if ($('#content-one').length) {
                $('#content-one').toggleClass('hided');
            }
            if ($('#content-two').length) {
                $('#content-two').toggleClass('hided-right');
            }
            $('.collapseBtn.rightbar').toggleClass('top shadow');

        }
        var tempInterval = setInterval(function() {
            $.egox.ui.tabs.resize();
        }, 0);
        setTimeout(function() {
            clearInterval(tempInterval);
        }, 500);
    });


    //------------- widget panel magic -------------//

    var widget = $('div.panel');
    var widgetOpen = $('div.panel').not('div.panel.closed');
    var widgetClose = $('div.panel.closed');
    //close all widgets with class "closed"
    widgetClose.find('div.panel-body').hide();
    widgetClose.find('.panel-heading>.minimize').removeClass('minimize').addClass('maximize');

    widget.find('.panel-heading>a').click(function(event) {
        event.preventDefault();
        var $this = $(this);
        if ($this.hasClass('minimize')) {
            //minimize content
            $this.removeClass('minimize').addClass('maximize');
            $this.parent('div').addClass('min');
            cont = $this.parent('div').next('div.panel-body');
            cont.slideUp(500, 'easeOutExpo'); //change effect if you want :)

        } else
        if ($this.hasClass('maximize')) {
            //minimize content
            $this.removeClass('maximize').addClass('minimize');
            $this.parent('div').removeClass('min');
            cont = $this.parent('div').next('div.panel-body');
            cont.slideDown(500, 'easeInExpo'); //change effect if you want :)
        }

    });

    //show minimize and maximize icons
    widget.hover(function() {
        $(this).find('.panel-heading>a').show(50);
    }
    , function() {
        $(this).find('.panel-heading>a').hide();
    });

    //add shadow if hover panel
    widget.not('.drag').hover(function() {
        $(this).addClass('hover');
    }
    , function() {
        $(this).removeClass('hover');
    });

    //------------- Tooltips -------------//

    //top tooltip
    $('.tip').qtip({
        content: false,
        position: {
            my: 'bottom center',
            at: 'top center',
            viewport: $(window)
        },
        style: {
            classes: 'qtip-tipsy'
        }
    });

    //tooltip in right
    $('.tipR').qtip({
        content: false,
        position: {
            my: 'left center',
            at: 'right center',
            viewport: $(window)
        },
        style: {
            classes: 'qtip-tipsy'
        }
    });

    //tooltip in bottom
    $('.tipB').qtip({
        content: false,
        position: {
            my: 'top center',
            at: 'bottom center',
            viewport: $(window)
        },
        style: {
            classes: 'qtip-tipsy'
        }
    });

    //tooltip in left
    $('.tipL').qtip({
        content: false,
        position: {
            my: 'right center',
            at: 'left center',
            viewport: $(window)
        },
        style: {
            classes: 'qtip-tipsy'
        }
    });

    //------------- Uniform  -------------//
    //add class .nostyle if not want uniform to style field
    $('.search-btn').addClass('nostyle');//tell uniform to not style this element
    $("input, textarea, select").not('.nostyle').uniform();

});