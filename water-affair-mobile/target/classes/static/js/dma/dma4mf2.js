$(document).ready(function(){


            var night_range=document.getElementById("night_range").value;
            var hours=night_range.split(":");

            //console.log( +"--------"++"--------"+hours[2]+"--------");

            $("#range_slider").noUiSlider({
                start: [ parseInt(hours[0].substring(5,7)), parseInt(hours[1].substring(3,5)) ],
                behaviour: 'drag',
                connect: true,
                range: {
                    'min':  0,
                    'max':  24
                },
                step: 1
            }).change(function(values, handle){

                var values = $(this).val();
                console.log(parseInt(values[0])+"------------"+parseInt(values[1]));
                var starthour="00";
                var endhour="03";
                if(values[0]<10){
                    starthour="0"+parseInt(values[0]);
                }
                else{
                    starthour=parseInt(values[0]);
                }

                if(values[1]<10){
                    endhour="0"+parseInt(values[1])
                }
                else{
                    endhour=parseInt(values[1]);
                }
                document.getElementById("night_range").value="夜间时段("+starthour+":00-"+endhour+":00)";
            });


            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-blue',
                radioClass: 'iradio_square-blue'
            });
            function getNowFormatDate () {
                var date = new Date();
                var separator = "-";
                var year = date.getFullYear();
                var month = date.getMonth() + 1;
                var strDate = date.getDate();
                if (month <= 9) {
                   month = '0' + month;
                }
                if (strDate <= 9) {
                    strDate = '0' + strDate;
                }
                var currentDate = year + separator + month + separator + strDate;
                return currentDate;
            }
            function getNowFormatMonth () {
                var date = new Date();
                var separator = "-";
                var year = date.getFullYear();
                var month = date.getMonth() + 1;
                if (month <= 9) {
                   month = '0' + month;
                }
                var currentMonth = year + separator + month;
                return currentMonth;
            }
            function getNowFormatMonthNext () {
                var date = new Date();
                var separator = "-";
                var year = date.getFullYear();
                var nextMonth = date.getMonth() + 2;
                if (nextMonth <= 9) {
                   nextMonth = '0' + nextMonth;
                }
                if (nextMonth > 12) {
                    nextMonth = '01';
                    year+=1;
                }
                var currentMonthNext = year + separator + nextMonth;
                return currentMonthNext;
            }
            var today = getNowFormatDate();
            var nowMonth = getNowFormatMonth();
            var nextMonth = getNowFormatMonthNext();
            var todayM = today + ' 00:00';
//            var todayN = today + ' 23:59';//经客户要求将时间限定为及时，不再写死为23点
            var date = new Date();
            var todayN = today + " "+date.getHours()+":"+date.getMinutes();
            var nowMonthStart = nowMonth + '-01 00:00';
            var NextMonthStart = nextMonth + '-01 00:00';
            //$('.input-todayM').val(todayM);
            //$('.input-todayN').val(todayN);
            $('.search-checks-day input').on('ifChecked',function () {
                $('.input-todayM').val(todayM);
                $('.input-todayN').val(todayN);
            });
            $('.search-checks-month input').on('ifChecked',function () {
                $('.input-todayM').val(nowMonthStart);
                $('.input-todayN').val(NextMonthStart);
            });
            $('.layer-date').click(function(){
                laydate({istime: true, format: 'YYYY-MM-DD hh:mm', choose: function(){
                    $('.i-checks input').iCheck('uncheck');
                }})
            })
        });