<!--<form action="@action@" method="@method@" name="@name@" onsubmit="return doSubmit()">
    <table cellpadding="0" cellspacing="0">
        <tr>
            <td><img src="images/ful.gif"></td>
            <td style="background-image: url('images/fuc.gif')"></td>
            <td><img src="images/fur.gif"></td>
        </tr>
        <tr>
            <td style="background-image: url('images/flc.gif')"></td>
            <td>
                <table>
                    @elements@
                    <th colspan=2><a style="cursor:default;">
                        <table cellpadding="0" cellspacing="0">
                            <tr>
                                <td><img src="images/but-l.gif"></td>
                                <td style="background-color:#495e79;background-image: url('images/but-c.gif') ">
                                    <input type="submit" style="border: 0px solid white; background-color:#495e79;"
                                           value="@submit_name@"/>
                                </td>
                                <td><img src="images/but-r.gif"></td>
                            </tr>
                        </table>
                    </a>
                    </th>
                </table>
            </td>
            <td style="background-image: url('images/frc.gif')"></td>
        </tr>
        <tr>
            <td valign="top"><img src="images/fld.gif"></td>
            <td valign=top style="background-image: url('images/fdc.gif')"></td>
            <td><img src="images/fdr.gif"></td>
        </tr>
    </table>
</form> -->

<form action="@action@" method="@method@" name="@name@" onsubmit="return doSubmit()">
       <table style="border:1px solid black;" cellpadding="0" cellspacing="0">
           <th colspan="2" class="header2" style="padding:5px">
               @form_user_name@
           </th>
           <tr style="padding:15px">
               <td align="center" style="background-color: #f2f4fa;">
                   <table>
                       @elements@
                   </table>
                   <input type="submit" style="background-color: #dbe1e8; border:2px solid black; margin-top: 10px;" value="@submit_name@"/>
               </td>
           </tr>
           <th colspan="2" class="header2" style="padding:5px">
               &nbsp
           </th>
       </table>
</form>