 use strict;

open FH, '<', $ARGV[0] 
     or die "Can not open file $ARGV[0]: $!";

my $i = 0;
my %h;


while (<FH>) {   # defined (my $_ = <FH> )
 
  while(/(\w+)/g){
  $h{$1}++;
} 
}

while(my ($k,$v)=each %h)
{print"$k -> $v\n"};
close FH;