 use strict;

open FH, '<', $ARGV[0] 
     or die "Can not open file $ARGV[0]: $!";

my $i = 0;


while (<FH>) {   # defined (my $_ = <FH> )
 $i++;  
if (m/Hello/){
   print "$i :$_";
 }
}

close FH;
