use strict;

my @a = qw/one two four five/;
my @b = qw/one two three five six/;
my @new;

my %h; # ключи - элементы массива @b, значения все равны 1

@h{@b} = ();

#foreach (@b) {
#   $h{$_} = undef;
#}

 

foreach my $n (@a) {
   if (exists $h{$n}) {
     push @new, $n;
   }
}

$, = ',';
print @new;



