#!/usr/bin/perl

use strict;

use constant {
    SCOPES => 100,
    SKIPS_PER_SCOPE => 100,
};

my $ifname = shift;
my $ofname = shift//'-';
my $ifile;
my $ofile;

my $subst;

if(!defined($ifname)){
    print STDERR "Usage: $0 <infile> [outfile]\n";
    exit(1);
}
if($ofname && $ofname ne '-'){
    open($ofile, ">", $ofname) or die("$ofname: $!");
} else {
    $ofile = \*STDOUT;
    $ofname = '';
}
$subst = generate_subst();
open($ifile, "<", $ifname) or die("$ifname: $!");

while( my $line = <$ifile> ) {
    $line =~ s|##\@IDLE_CODE\@;?|$subst;|og;
    print $ofile $line;
}

close($ifile);
if($ofile){
    close($ofile) or die("$ofile: $!");
}

exit(0);

sub generate_subst{
    my $skips = 'skip;' x (SKIPS_PER_SCOPE - 1) . "skip";
    
    return "{$skips}";
}
