import subprocess
import os
import glob
import re
from datetime import datetime


STATUS_REPORT_BASE = 'status-reports'
STATUS_REPORT_NAME = 'SR'
DATE_FMT_GIT = '%a %b %d %H:%M:%S %Y %z'
DATE_FMT_STATUS_REPORT = '%Y-%m-%d %H:%M:%S'


def update_status_report(commits: dict, document: str) -> None:
    if not os.path.exists(STATUS_REPORT_BASE):
        os.mkdir(STATUS_REPORT_BASE)

    filename = f'{STATUS_REPORT_NAME}_{document}.csv'
    filepath = os.path.join(STATUS_REPORT_BASE, 'csv', filename)

    with open(filepath, 'w') as f:
        f.write(f'{"version,":10}{"author, ":20}{"date, ":25}msg\n')
        for version, commit in enumerate(commits[::-1]):
            f.write(f"{'0.%s,' % version:10}"
                    f"{commit['author']+',':20}"
                    f"{commit['date']+',':25}"
                    f"{commit['msg']}\n")
        f.write('\n')

    return filepath

def clean_commits(commits):
    for commit in commits:
        date = datetime.strptime(commit['date'].strip(), DATE_FMT_GIT)
        commit['date'] = date.strftime(DATE_FMT_STATUS_REPORT)
        commit['msg'] = commit['msg'].strip()
        commit['author'] = commit['author'].strip()
    
    return commits


def get_commits(document: str) -> dict:
    res = subprocess.run(['git', 'log', document], 
                         stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    if res.stderr:
        print(res.stderr)
    res = res.stdout.decode('utf-8').split('\n\n')
    i = 0
    commits = []

    try:
        while i < len(res):
            info = res[i].split('\n')
            msg = res[i+1]
            commits.append(
                {
                    'sha': info[0].split(' ')[1],
                    'author': info[1].split(' ')[1],
                    'date': info[2].split('Date: ')[1].strip(),
                    'msg': msg
                }
            )
            i += 2
    except IndexError:
        print(f'Failed to get commits from "{document}"')

    return clean_commits(commits)


def update_status_reports():
    for phase, documents in DOCUMENTS.items():
        for doc in documents:
            
            # Document filepaths are just the document names, so
            # we need to use wildcard for the version.
            filepath = os.path.join(phase, doc)
            filepath = glob.glob(f'{filepath}*')[0]
            commits = get_commits(filepath)
            update_status_report(commits, filepath)


if __name__ == '__main__':
    #update_status_reports()  
    changed_docs = get_changed_docs()
    update_document_versions(changed_docs)
    # git commit --amend --no-edit 