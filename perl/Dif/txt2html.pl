#!/usr/bin/perl
#This's a programm which cahge '<' on &tl, 'on' &gt, '&' on &amp and 'http://www...' on <a hreg="http://www...">http://www...</a>

die "Usage: perl txt2html.pl Infile Outfile\n" unless(@ARGV);
open (IN,"$ARGV[0]")|| die || "Cannot open $argv[0] $! \n";
open (OUT,">$ARGV[1]")|| die || "Cannot open $argv[1] $! \n";

while(<IN>)
{
 s/&/&amp;/g;
 s/</&lt;/g;
 s/>/&gt;/g;
 s/(http:\/\/\S+)/<a href=|$1">$1<\/a>/g;
 s/(www\S+)/<a href=|$1">$1<\/a>/g;
 print OUT $_;
}close(IN);
close(OUT);
