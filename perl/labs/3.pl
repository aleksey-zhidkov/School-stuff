use strict;

open FH, '<', $ARGV[0] 
     or die "Can not open file $ARGV[0]: $!";

my %h = ();
while (<FH>) {
  if ($_ eq "\n") {
     
      if ($h{'lname'} eq 'Pupkin') {
        while(my ($k,$v)=each %h)
        {print"$k -> $v\n"};}
           %h=();    
  } else {
     /(.*?):(.*)/;
     $h{$1}=$2;
  }
}
