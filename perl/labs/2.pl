use strict;

my @a = qw/one two four five/;
my @b = qw/one two three five six/;
my @new = @a;
my %h;
@h{@a}=();


foreach my $n (@b) {
   if (!exists $h{$n}) {
     push @new, $n;
   }
}


$, = ',';
print @new;

