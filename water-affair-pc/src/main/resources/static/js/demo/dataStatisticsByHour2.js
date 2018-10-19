$(document).ready(function(){


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
            $('.input-todayM').val(todayM);
            $('.input-todayN').val(todayN);
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