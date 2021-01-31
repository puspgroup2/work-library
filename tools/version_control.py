from datetime import datetime
import glob
import os
import pdfkit
import re
import subprocess
import sys

import commit_utils


DOCUMENTS = {
    'P0-No-Baselines': ['PUSS214209_CML', 'PUSS214210_IRP'],
    'P1-Baseline-Specification': ['PUSS214200_SDP', 'PUSS214201_SRS', 'PUSS214202_SVVS'],
    'P2-Baseline-Desgin-Test': ['PUSS214203_SVVI', 'PUSS214204_STLDD'],
    'P3-Baseline-Informal': ['PUSS214205_SDDD'],
    'P4-Baseline-Product': ['PUSS214206_SVVR', 'PUSS214207_SSD', 'PUSS214208_PFR']
}


def get_version(document):
    res = subprocess.run(['git', 'log', '--oneline', document], stderr=subprocess.PIPE,
                         stdout=subprocess.PIPE)
    if res.stderr:
        print(res.stderr)

    version = len(res.stdout.decode('utf-8').splitlines())
    return version


def update_version_file(documentpath, version):
    versionfile = os.path.join(documentpath, 'version')
    with open(versionfile, 'w') as f:
        f.write(f'0.{version}')


def git_add(changed_document):
    res = subprocess.run(['git', 'add', changed_document], stderr=subprocess.PIPE)
    if res.stderr:
        print(res.stderr)


def git_commit(amend=False, msg='Auto-commit'):
    """ Adds the last changes to the last commit. """
    if amend:
        res = subprocess.run(['git', 'commit', '--amend', '--no-edit'], 
                            stdout=subprocess.PIPE)
    else:
        res = subprocess.run(['git', 'commit', '-m', msg], 
                            stdout=subprocess.PIPE)
    if res.stderr:
        print(res.stderr)


def build_versions():
    versions = {}
    # Build filepaths and read the units versions.
    # If the unit doesn't have a version file, we create one, starting at v.0.0
    for phase, documents in DOCUMENTS.items():
        for document in documents:
            documentpath = os.path.join(phase, document)
            versionfile = os.path.join(documentpath, 'version')
            try:
                with open(versionfile, 'r') as f:
                    version = f.read().strip()
            except FileNotFoundError as e:
                version = '0.0'

            versions[documentpath] = version
    
    return versions


def update_version_files(versions, commit=False):
    # Compare the old versions with the new one and update
    # the old ones (should not be more than one at a time...!).
    for documentpath, version in versions.items():
        # Version defined by number of commits regarding the unit.
        new_version = get_version(documentpath)      
        old_version = int(version.split('.')[1])
        if old_version != new_version:
            update_version_file(documentpath, new_version)
            if commit:
                git_add(documentpath)
    if commit:
        git_commit(amend=True)
    

def create_version_files(versions, commit=False):
    for documentpath, version in versions.items():
        new_version = get_version(documentpath)
        update_version_file(documentpath, new_version)
        if commit:
            git_add(documentpath)
    if commit:
        git_commit(amend=False, msg='Initialized version files.')


def update_status_reports(versions):
    reports = []
    for documentpath, file_version in versions.items():
        commits = commit_utils.get_commits(documentpath)
        document = os.path.basename(documentpath)
        reportpath = commit_utils.update_status_report(commits, document)
        reports.append(reportpath)
    return reports


def build_pdfs(reports):
    for csv in reports:
        pdf = csv.replace('csv', 'pdf')
        pdfkit.from_file(csv, pdf)


if __name__ == '__main__':

    versions = build_versions()

    if len (sys.argv) > 1:
        cmd = sys.argv[1]

        if cmd == 'update':
            update_version_files(versions)

        elif cmd == 'status-reports':
            reports = update_status_reports(versions)
            build_pdfs(reports)
            git_add(commit_utils.STATUS_REPORT_BASE)
            git_commit(msg='Auto-generated status-reports.')

        elif cmd == 'init':
            create_version_files(versions)

        elif cmd == 'info':
            for documentpath, file_version in versions.items():
                version = get_version(documentpath)
                print(f'{documentpath}: {file_version} -> {version}')
